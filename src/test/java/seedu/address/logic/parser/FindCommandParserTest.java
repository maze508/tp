package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.NameAndTagContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // Test for searching names using /n prefix
        List<String> nameKeywords = Arrays.asList("Alice", "Bob");
        // No Tags
        List<String> emptyList = Collections.emptyList();
        NameAndTagContainsKeywordsPredicate predicateForNameSearch =
                new NameAndTagContainsKeywordsPredicate(nameKeywords, emptyList);
        FindCommand expectedFindCommandForName = new FindCommand(predicateForNameSearch);

        // Test parsing for name search
        assertParseSuccess(parser, "n/ Alice n/ Bob", expectedFindCommandForName);

        // Test for searching tags using /t prefix
        List<String> tagKeywords = Arrays.asList("friend", "colleague");
        NameAndTagContainsKeywordsPredicate predicateForTagSearch =
                new NameAndTagContainsKeywordsPredicate(emptyList, tagKeywords);
        FindCommand expectedFindCommandForTag = new FindCommand(predicateForTagSearch);

        // Test parsing for tag search
        assertParseSuccess(parser, "t/ friend t/ colleague", expectedFindCommandForTag);

        // Test for searching both names and tags
        NameAndTagContainsKeywordsPredicate predicateForBothSearch =
                new NameAndTagContainsKeywordsPredicate(nameKeywords, tagKeywords);
        FindCommand expectedFindCommandForBoth = new FindCommand(predicateForBothSearch);

        // Test parsing for both names and tags search
        assertParseSuccess(parser, "n/ Alice n/ Bob t/ friend t/ colleague", expectedFindCommandForBoth);
    }

    @Test
    public void parse_noKeywordsProvided_throwsParseException() {
        // Test case with no keywords provided for both /n and /t prefixes
        assertParseFailure(parser, "n/ t/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        // Test case with /n prefix but no name keywords
        assertParseFailure(parser, "n/ ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        // Test case with /t prefix but no tag keywords
        assertParseFailure(parser, "t/ ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        // Test case with both /n and /t prefixes but no keywords
        assertParseFailure(parser, "n/ t/ ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

}
