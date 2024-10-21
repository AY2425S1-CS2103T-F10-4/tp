package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.BookmarkCommand.MESSAGE_BOOKMARK_FAILURE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showCompanyAtIndex;
import static seedu.address.testutil.TypicalBookmarkedCompanies.getTypicalBookmarkedAddressBook;
import static seedu.address.testutil.TypicalCompanies.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_COMPANY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_COMPANY;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.company.Bookmark;
import seedu.address.model.company.Company;


public class BookmarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model bookmarkedModel = new ModelManager(getTypicalBookmarkedAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Company companyToBookmark = model.getFilteredCompanyList().get(INDEX_FIRST_COMPANY.getZeroBased());
        BookmarkCommand bookmarkCommand = new BookmarkCommand(INDEX_FIRST_COMPANY);

        String expectedMessage = String.format(BookmarkCommand.MESSAGE_BOOKMARK_SUCCESS,
                Messages.format(companyToBookmark));

        Company companyBookmarked = new Company(companyToBookmark.getName(), companyToBookmark.getPhone(),
                companyToBookmark.getEmail(), companyToBookmark.getAddress(), companyToBookmark.getCareerPageUrl(),
                companyToBookmark.getTags(), new Bookmark(true));
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        expectedModel.setCompany(companyToBookmark, companyBookmarked);

        assertCommandSuccess(bookmarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_existingBookmarkUnfilteredList_throwsCommandException() {
        BookmarkCommand bookmarkCommand = new BookmarkCommand(INDEX_FIRST_COMPANY);

        assertCommandFailure(bookmarkCommand, bookmarkedModel, MESSAGE_BOOKMARK_FAILURE);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCompanyList().size() + 1);
        BookmarkCommand bookmarkCommand = new BookmarkCommand(outOfBoundIndex);

        assertCommandFailure(bookmarkCommand, model, Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showCompanyAtIndex(model, INDEX_FIRST_COMPANY);

        Company companyToBookmark = model.getFilteredCompanyList().get(INDEX_FIRST_COMPANY.getZeroBased());
        BookmarkCommand bookmarkCommand = new BookmarkCommand(INDEX_FIRST_COMPANY);

        String expectedMessage = String.format(BookmarkCommand.MESSAGE_BOOKMARK_SUCCESS,
                Messages.format(companyToBookmark));

        Company companyBookmarked = new Company(companyToBookmark.getName(), companyToBookmark.getPhone(),
                companyToBookmark.getEmail(), companyToBookmark.getAddress(), companyToBookmark.getCareerPageUrl(),
                companyToBookmark.getTags(), new Bookmark(true));
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        expectedModel.setCompany(companyToBookmark, companyBookmarked);

        assertCommandSuccess(bookmarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_existingBookmarkFilteredList_throwsCommandException() {
        showCompanyAtIndex(bookmarkedModel, INDEX_FIRST_COMPANY);
        BookmarkCommand bookmarkCommand = new BookmarkCommand(INDEX_FIRST_COMPANY);

        assertCommandFailure(bookmarkCommand, bookmarkedModel, MESSAGE_BOOKMARK_FAILURE);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showCompanyAtIndex(model, INDEX_FIRST_COMPANY);

        Index outOfBoundIndex = INDEX_SECOND_COMPANY;
        // Ensures that out of bound index is still within bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getCompanyList().size());
        BookmarkCommand bookmarkCommand = new BookmarkCommand(outOfBoundIndex);

        assertCommandFailure(bookmarkCommand, model, Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
    }
}
