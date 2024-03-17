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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StackOverflowUpdatesExtractor implements UpdatesExtractor {
    private static final String FILTER = "!.D4(1w)YlipX6OPO1pl1(bIhbp*I9AKZhioPZfjMspO8hRG.8djruHpSgxZ-l_ekoulUVCR3mfn";

    @Autowired
    StackOverflowClient client;

    @SuppressWarnings("RegexpSingleline")
    private static final String TEMPLATE = """
            Событие для вопроса [%s](%s)
                            
            Тип события: %s
            Автор: [%s](%s)
            Дата и время: %s
            """;

    @Override
    public List<Update> getUpdates(URI url, OffsetDateTime timestamp) {
        String regex = "https://stackoverflow.com/questions/(\\d+)/.*";
        Matcher matcher = Pattern.compile(regex).matcher(url.toString());

        if (!matcher.find()) {
            return List.of();
        }

        int questionId = Integer.parseInt(matcher.group(1));

        List<Update> updates = new ArrayList<>();

        GetQuestionsByIdsRequest request = new GetQuestionsByIdsRequest();
        request.setPageSize(1);
        request.setFilter(FILTER);

        Question question = client.getQuestionsByIds(new int[]{questionId}, request).getFirst();

        if (question == null) {
            return updates;
        }

        for (Comment comment: question.comments()) {
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

        for (Answer answer: question.answers()) {
            for (Comment comment: answer.comments()) {
                if (comment.creationDate().isBefore(timestamp)) {
                    continue;
                }

                updates.add(new Update(url, String.format(
                        TEMPLATE,
                        question.title(),
                        question.link(),
                        "answer comment",
                        comment.owner().displayName(),
                        comment.owner().link(),
                        comment.creationDate()
                )));
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
