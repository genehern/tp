---
layout: page
title: User Guide
---

**ClassConnect** is a **desktop application** designed for **private tuition teachers** to efficiently manage their contacts.<br><br>
It helps tutors organize information about their **students and their parents**, keeping communication and record-keeping simple and centralized.

## Key Features
<img src="images/Ui.png" alt="Ui" width="500" style="height:auto;">

- **Add and manage contacts** — Create and update entries for both students and parents.
- **Link students to parents** — Maintain clear relationships between students and their respective parents within the app.
- **Tag students for easy organization** — Assign tags such as `Math`, `P6`, or `Progressing` to categorize students effectively.

--------------------------------------------------------------------------------------------------------------------

## Table of Contents
* [Quick start](#quick-start)
* [Commands](#commands)
* [FAQ](#faq)
* [Current limitations](#current-limitations)

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

2. Download the latest `.jar` file from [here](https://github.com/AY2526S1-CS2103T-F10-4/tp/releases).

3. Copy the file to a folder of your choice.

4. Open the command terminal in this folder and enter `java -jar ClassConnect.jar` to run the application.<br>
   An interface similar to the one shown above should appear in a few seconds. The app comes with some sample data.<br>

5. Refer to the [Commands section](#commands) below for details of available commands.<br> 
   Enter them in the command box and have experiment using the sample data!

--------------------------------------------------------------------------------------------------------------------

## Commands

### Parameter Summary

| Parameter | Description | Example | Constraints |
|------------|--------------|----------|--------------|
| `n/` | Name | `n/John Tan` | Names should only contain alphabets, spaces, apostrophes (`'`), and hyphens (`-`).<br>It is **compulsory** and should be at most **60 characters** long.<br>If a `/` is included in the name, replace it with `-`. |
| `p/` | Phone number | `p/+65 9876 5432` | Phone numbers should only contain digits, spaces, brackets `()`, plus signs (`+`), and hyphens (`-`).<br>It is **compulsory** and should be **3–25 characters** long. |
| `e/` | Email address | `e/johntan@example.com` | Emails should follow the format `local-part@domain-part`.<br>It is **compulsory** and should be at most **320 characters** long.<br>The domain part must be at least **2 characters** long. |
| `a/` | Physical address | `a/123, Clementi Rd, #12-34, Singapore 123456` | Addresses can contain any character.<br>It is **compulsory** and should be at most **300 characters** long. |
| `r/` | Remark | `r/Available on weekends` | Remarks can contain any character except `/` or a newline (`\n`).<br>It is **optional** but should be at most **250 characters** long. |
| `t/` | Tag for student contacts | `t/Math` | Tags should only contain alphabets, digits, and underscores (`_`).<br>It is **optional** but should be at most **30 characters** long.<br>Only tag values **already added** and shown in the “Tags” list on the right of the interface can be added to student contacts. |
| `sn/` | Student name for linking | `sn/John Tan` | Should follow the same constraints as `n/` above. |
| `pn/` | Parent name for linking | `pn/Mrs Tan` | Should follow the same constraints as `n/` above. |

### Summary
<div markdown="span" class="alert alert-primary">
:bulb: **Tip:**
All parameters shown in the commands are compulsory unless denoted by square brackets `[]`.
For optional parameters denoted with `...`, multiple of said parameter can be specified.
</div>

Action | Description                     | Format, Examples
-------|---------------------------------|-------------
**[Adds](#adding-a-contact-addsaddp)** | Add a student contact           | `adds n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [r/REMARK] [t/TAG]…​` <br> e.g., `adds n/James Ho p/87492563 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/Math`
**[Addp](#adding-a-contact-addsaddp)** | Add a parent contact            | `addp n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [r/REMARK]​` <br> e.g., `addp n/Jane Doe p/97549673 e/janedoe@example.com a/321, Jurong West St 74 r/Avail on weekends`
**[Edit](#editing-a-contact--edit)** | Edit a contact's fields         | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [r/REMARK] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**[Delete](#deleting-a-person--delete)** | Delete a contact                | `delete INDEX or delete [n/NAME] [e/EMAIL] [p/PHONE]` <br> e.g., `delete 3, delete n/Alex Yeoh, delete e/alex@example.com, delete n/Alex Yeoh e/alex@example.com`
**[Clear](#clearing-all-entries--clear)** | Clear all contact(s)            | `clear`
**[Link](#linking-a-student-and-parent--link)** | Establish a parent-student link | `link sn/STUDENT_NAME pn/PARENT_NAME` <br> e.g., `link sn/John Tan pn/Mrs Tan`
**[Unlink](#unlinking-a-student-and-parent--unlink)** | Break a parent-student link     | `unlink sn/STUDENT_NAME pn/PARENT_NAME` <br> e.g., `unlink sn/John Tan pn/Mrs Tan`
**[Findlink](#finding-linked-contacts--findlink)** | Find linked contacts            | `findlink n/NAME`<br> e.g., `findlink n/Alice Tan`
**[Addtag](#adding-tags--addtag)** | Add tag(s) to the tag list      | `addtag t/TAG`<br> e.g., `addtag t/Math`
**[Deletetag](#deleting-tags--deletetag)** | Delete tag(s) from the tag list | `deletetag t/TAG`<br> e.g., `deletetag t/Math`
**[List](#listing-all-contacts--list)** | Show all contact(s)             | `list`
**[Find](#locating-contacts-by-name-find)** | Find contact(s) by name(s)      | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**[Filter](#filtering-by-tags-filter)** | Find contacts by tag(s)         | `filter TAG [MORE_TAGS]`<br> e.g., `filter Math Science`
**[Help](#viewing-help--help)** | Opens the user guide            | `help`
**[Exit](#exiting-the-program--exit)** | Exit the application            | `exit`

### Adding a contact: `adds`/`addp`

Adds a student (with `adds`) or a parent (with `addp`) to the address book.<br>

Format (Parent): `addp n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [r/REMARK]` <br>
Format (Student): `adds n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [r/REMARK] [t/TAG]…`<br>

<div markdown="span" class="alert alert-primary">
:bulb: **Tip:**
The order of fields does not matter. e.g. `adds p/98765432 n/John Doe...` is also acceptable.
</div>

- Both student and parent contacts contain the following **compulsory** fields:
  - `name`, set with the prefix `n/`
  - `phone number`, set with the prefix `p/`
  - `email`, set with the prefix `e/`
  - `address`, set with the prefix `a/`<br>

- Optionally, a `remark` field can also be added with `r/`.<br>
- For **students**, additional **_optional_** `tag` fields can be added with `t/` to organise them.<br>
  - A tag MUST be added with `addtag` before it can be assigned to a contact.<br>
    See the `addtag` section below for more details.
  - Multiple tags can be added by repeating the `t/` prefix.<br>

For parameter constraints, please refer to the [Command Parameter Summary](#command-parameter-summary) table above.

<div markdown="span" class="alert alert-primary">
:bulb: **Tip:** A tag must be added with `addtag` before it can be assigned to a contact.<br>
See the `addtag` section below for more details.
</div>

<div markdown="span" class="alert alert-primary">
Multiple contacts with **different names** can have the same information fields (phone number, email...) as family members may share a phone or email.
</div>

Examples:
* `adds n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 t/Math`
* `addp n/Betsy Crowe e/betsycrowe@example.com a/Newgate Prison p/1234567`

<div style="display: flex; justify-content: center; align-items: flex-start; gap: 20px; flex-wrap: wrap;">
  <div style="flex: 1; min-width: 300px;">
    <img src="images/AddJohnDoe.png" alt="add john doe" style="width: 100%; height: auto; border-radius: 6px;">
    <p align="center"><em>Before adding (command entered)</em></p>
  </div>

  <div style="flex: 1; min-width: 300px;">
    <img src="images/AddedJohnDoe.png" alt="added john doe" style="width: 100%; height: auto; border-radius: 6px;">
    <p align="center"><em>After adding (contact successfully shows up)</em></p>
  </div>
</div>

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
ClassConnect **prohibits** multiple contacts with the exact same name, 
even if the capitalisation or information fields (phone number, email...) are different.
</div>


### Editing a contact : `edit`

Edits an existing contact in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [r/REMARK] [t/TAG]…​`

* Edits the person positioned at the specified `INDEX` in the contact list. The index **must be a positive integer** 1, 2, 3, …​
* At least **_one_** of the optional fields must be provided.
* Existing values will be **overridden** by the input values.
  * When editing tags, the existing tags of the person will be replaced.

For parameter constraints, please refer to the [Command Parameter Summary](#command-parameter-summary) table above.

<div markdown="span" class="alert alert-primary">
:bulb: **Tip:**
You can remove all the person’s tags by typing `t/` without specifying any tags after it. You can also do the same with remark by typing `/r` without specifying a remark after.<br>
</div>
<div markdown="span" class="alert alert-primary">
:bulb: **Tip:**
Only student contacts can have tags, so updates to the tag with `t/` only applies to student contacts.<br>
</div>

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

Note:
* You **cannot** edit a contact name to another existing contact name (case insensitive) since ClassConnect does not accept duplicate names (even if the other info is different).

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX` or `delete [n/NAME] [e/EMAIL] [p/PHONE]`

* Deletes the person at the specified INDEX, or deletes the person that matches the provided identifier(s).
* The index refers to the number shown in the current displayed person list.
* The index must be a positive number 1, 2, 3, …
* When using identifiers:
* `n/NAME` — matches the full name (case-insensitive).
* `e/EMAIL` — matches the exact email address (case-insensitive).
* `p/PHONE` — matches the exact phone number.
* You must use either the index form or the identifier form — not both in the same command.
* If multiple people match, the app reports that the command is ambiguous and requests you to specify more identifiers.
* If no match is found, the app will show a no match found message.

For parameter constraints, please refer to the [Command Parameter Summary](#command-parameter-summary) table above.

<div markdown="span" class="alert alert-primary">
:bulb: **Tip:**
If you are unsure of a contact’s full name, use identifiers such as email or phone number to avoid deleting the wrong person.
</div>

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.
* `delete n/John Doe` deletes the person named “John Doe”.
* `delete e/alice@example.com` deletes the person with that email.
* `delete p/91234567` deletes the person with that phone number.
* `delete n/John Tan e/john.tan@example.com` deletes the correct “John Tan” by email.

For parameter constraints, please refer to the [Command Parameter Summary](#command-parameter-summary) table above.

<div style="display: flex; justify-content: center; align-items: flex-start; gap: 20px; flex-wrap: wrap;">

  <div style="flex: 1; min-width: 300px;">
    <img src="images/deletebyindex.png" alt="link alex john" style="width: 100%; height: auto; border-radius: 6px;">
    <p align="center"><em>Before delete by index (command entered)</em></p>
  </div>

  <div style="flex: 1; min-width: 300px;">
    <img src="images/deletedbyindex.png" alt="linked alex john" style="width: 100%; height: auto; border-radius: 6px;">
    <p align="center"><em>After deleting (contact deleted successfully)</em></p>
  </div>

</div>

<div style="display: flex; justify-content: center; align-items: flex-start; gap: 20px; flex-wrap: wrap;">

  <div style="flex: 1; min-width: 300px;">
    <img src="images/deletebyidentifier.png" alt="link alex john" style="width: 100%; height: auto; border-radius: 6px;">
    <p align="center"><em>Before delete by identifier (command entered)</em></p>
  </div>

  <div style="flex: 1; min-width: 300px;">
    <img src="images/deletedbyidentifier.png" alt="linked alex john" style="width: 100%; height: auto; border-radius: 6px;">
    <p align="center"><em>After delete (contact deleted successfully)</em></p>
  </div>

</div>

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`


### Linking a student and parent : `link`

Links an existing student contact with a parent contact so that their contacts will display each other's name.

Format: `link sn/STUDENT_NAME pn/PARENT_NAME`

* `sn/` — name of the student contact (required)
* `pn/` — name of the parent contact (required)

For parameter constraints, please refer to the [Command Parameter Summary](#command-parameter-summary) table above.

Examples:
* `link sn/Alex Jones pn/John Doe` Links student Alex Jones with parent John Doe.

<div style="display: flex; justify-content: center; align-items: flex-start; gap: 20px; flex-wrap: wrap;">

  <div style="flex: 1; min-width: 300px;">
    <img src="images/linkAlexJohn.png" alt="link alex john" style="width: 100%; height: auto; border-radius: 6px;">
    <p align="center"><em>Before linking (command entered)</em></p>
  </div>

  <div style="flex: 1; min-width: 300px;">
    <img src="images/linkedAlexJohn.png" alt="linked alex john" style="width: 100%; height: auto; border-radius: 6px;">
    <p align="center"><em>After linking (contacts linked successfully)</em></p>
  </div>

</div>

Notes:
* A student can be linked to a maximum of 2 parents (e.g., mother and father).
* A parent can be linked to multiple students (e.g., their children).
* Link relationships are automatically saved in the data file and reloaded when the app restarts.
* Deleting a contact automatically removes all links associated with that contact.
* ⚠️ GUI update delay: When a link is created, it may take a short while for the linked names to appear under each contact due to internal update latency. If the change does not appear immediately, try running another command (e.g. list) to refresh the display.

### Unlinking a student and parent : `unlink`
Removes an existing link between a student and a parent contact, so you cannot see their names under each other's contact.

Format: `unlink sn/STUDENT_NAME pn/PARENT_NAME`

* `sn/` — name of the student contact (required)
* `pn/` — name of the parent contact (required)

For parameter constraints, please refer to the [Command Parameter Summary](#command-parameter-summary) table above.

Examples:
* `unlink sn/Alex Jones pn/John Doe` Removes the link between student Alex Jones and parent John doe.
* ⚠️ GUI update delay: The removal of linked names may take a short moment to display due to latency. You can run a list command to refresh the view immediately.

### Finding linked contacts : `findlink`

Finds and displays all contacts that are linked to a specified person.

Format: `findlink n/NAME`

* Finds people who are linked to the specified person. 
  For example, if a student is linked to their parents, this command will display the parents’ contacts.
* Name matching is case-insensitive e.g. findlink n/Alice Tan, findlink n/aLiCe tAn both work
* The n/ prefix is required 
* If the person exists but has no linked contacts, the command will show that there are 0 linked results. 
* If no person matches the given name, an error message will be displayed.

For parameter constraints, please refer to the [Command Parameter Summary](#command-parameter-summary) table above.

<div markdown="span" class="alert alert-primary">
:bulb: **Tip:**
If multiple contacts share similar names, ensure you specify the correct full name to accurately retrieve linked contacts.
</div>

Examples:
* `findlink n/Alice Tan` shows all contacts linked to Alice Tan (e.g. her parents).
* `findlink n/Tom Tan` shows all contacts linked to Tom Tan (e.g. his children).
* `findlink n/Bob Lee` shows “0 linked contact(s) for Bob Lee” if Bob Lee has no linked persons.
* `findlink n/Nonexistent Person` shows an error message indicating that no such person exists.

### Adding tags : `addtag`
Adds new tags to the possible tags list. You can add multiple tags at once.

Format: `addtag t/TAG t/TAG`

* `t/` — name of the tag to add (required)

For parameter constraints, please refer to the [Command Parameter Summary](#command-parameter-summary) table above.

Examples:
* `addtag t/Math t/Science` — Adds the tags `Math` and `Science` to the possible tags list.

### Deleting tags : `deletetag`
Deletes tags from the possible tags list. This will also remove the tag from all contacts who have it. You can delete multiple tags at once.

Format: `deletetag t/TAG t/TAG`

* `t/` — name of the tag to delete (required)

Examples:
* `deletetag t/Math t/Science` — Deletes the tags `Math` and `Science` from the possible tags list and from all contacts who had them.

### Listing all contacts : `list`

Displays all contacts in the address book.

Format: `list`

### Locating contacts by name: `find`

Finds contacts whose names contain any of the given keywords (names).

Format: `find KEYWORD [MORE_KEYWORDS]`

* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be shown.
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex john` returns `Alex jones`, `John Doe`<br>
  <img src="images/findAlexJohnResult.png" alt="find alex john" width="400" style="height:auto;">

### Filtering by tags: `filter`

Show students who have any of the tags given.

Format: `filter tag [MORE_TAGS]`

* The search is case-insensitive. e.g `Math` will match `math`
* The order of the tags does not matter.
* Only the tags are searched.
* Only full words will be matched e.g. `Math` will not match `Mathematics`
* Students with at least one tag will be shown.

Examples:
* John Doe has Math tag, Peter has Chem and Math tags
* `filter Chem` shows `Peter`
* `filter Math Chem` shows `John Doe` and `Peter`

Note:
* Since only student contacts can have tags, filtering will only display student contacts

### Viewing help : `help`

Shows a message explaining how to access the help page.

<img src="images/helpMessage.png" alt="help" width="400" style="height:auto;">
Format: `help`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

ClassConnect data is saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

ClassConnect data is saved automatically as a JSON file `[JAR file location]/data/ClassConnnect.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, ClassConnect will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the ClassConnect to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>


--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous ClassConnect home folder.

**Q**: I closed ClassConnect by clicking the window’s close button instead of typing the exit command. Will I lose my work?<br>
**A**: Don’t worry, ClassConnect saves your updates automatically as you go. When you reopen the app, your most recent changes should still be there.

**Q**: Can I use ClassConnect on my phone or tablet?<br>
**A**: Not at the moment. ClassConnect is currently available only on desktop computers.

**Q**: What happens if a student leaves and I delete their contact?<br>
**A**: Only the student is removed. Their parents remain safely in the address book — the family link is simply removed.

--------------------------------------------------------------------------------------------------------------------

## Current limitations

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

