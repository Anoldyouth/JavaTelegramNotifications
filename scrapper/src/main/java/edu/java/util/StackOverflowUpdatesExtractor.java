package edu.java.util;

import edu.java.client.stackoverflow.StackOverflowClient;
import edu.java.client.stackoverflow.dto.request.GetQuestionsByIdsRequest;
import edu.java.client.stackoverflow.dto.response.Answer;
import edu.java.client.stackoverflow.dto.response.Comment;
import edu.java.client.stackoverflow.dto.response.Question;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StackOverflowUpdatesExtractor implements UpdatesExtractor {
    private static final String FILTER = "!.D4(1w)YlipblLzxCCZrf.XUloaPKEW-l5FYoEnrw5EEjtWRmU0AhUUU3xApm0WA1RiUblytnpv";

    private final StackOverflowClient client;

    private URI url;
    private OffsetDateTime timestamp;

    @SuppressWarnings("RegexpSingleline")
    private static final String TEMPLATE = """
            Событие для вопроса [%s](%s)
                            
            Тип события: %s
            Автор: [%s](%s)
            Дата и время: %s
            """;

    @Override
    public List<Update> getUpdates(URI url, OffsetDateTime timestamp) {
        this.url = url;
        this.timestamp = timestamp;

        String regex = "^(?:https?://)?(?:www\\.)?stackoverflow\\.com/questions/(\\d+).*";
        Matcher matcher = Pattern.compile(regex).matcher(url.toString());

        if (!matcher.find()) {
            return List.of();
        }

        int questionId = Integer.parseInt(matcher.group(1));

        List<Update> updates = new ArrayList<>();

        GetQuestionsByIdsRequest request = new GetQuestionsByIdsRequest();
        request.setPageSize(1);
        request.setFilter(FILTER);

        var items = client.getQuestionsByIds(new int[]{questionId}, request).items();
        Question question = items.getFirst();

        if (question == null) {
            return updates;
        }

        if (question.comments() != null) {
            updates.addAll(checkComments(question.comments(), question));
        }

        if (question.answers() != null) {
            updates.addAll(checkAnswers(question.answers(), question));
        }

        return updates;
    }

    private List<Update> checkComments(List<Comment> comments, Question question) {
        List<Update> updates = new ArrayList<>();

        for (Comment comment: comments) {
            if (comment.creationDate().isBefore(timestamp)) {
                continue;
            }

            updates.add(new Update(url, String.format(
                    TEMPLATE,
                    question.title(),
                    question.link(),
                    "question comment",
                    comment.owner().displayName(),
                    comment.owner().link(),
                    comment.creationDate()
            )));
        }

        return updates;
    }

    private List<Update> checkAnswers(List<Answer> answers, Question question) {
        List<Update> updates = new ArrayList<>();

        for (Answer answer: question.answers()) {
            if (answer.comments() != null) {
                updates.addAll(checkComments(answer.comments(), question));
            }

            if (answer.creationDate().isBefore(timestamp)) {
                continue;
            }

            updates.add(new Update(url, String.format(
                    TEMPLATE,
                    question.title(),
                    question.link(),
                    "answer",
                    answer.owner().displayName(),
                    answer.owner().link(),
                    answer.creationDate()
            )));
        }

        return updates;
    }
}
