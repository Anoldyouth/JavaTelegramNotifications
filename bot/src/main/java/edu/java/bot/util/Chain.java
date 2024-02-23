package edu.java.bot.util;

import edu.java.bot.util.action.Action;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;

@Getter
public class Chain {
    private final List<Action> chain;

    public Chain(Action... chain) {
        this.chain = new LinkedList<>(Arrays.asList(chain));
    }
}
