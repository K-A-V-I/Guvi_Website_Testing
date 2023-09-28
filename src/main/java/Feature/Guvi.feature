Feature: Testing the Guvi website

 @homepage
  Scenario: Verify homepage content and layout
    Given I am on the homepage
    Then the homepage content and layout should be displayed correctly

@login
  Scenario Outline: Login with valid credentials
    Given I am on the login page
    When I enter valid login credentials with data from Excel at Sheet <SheetNumber> and Row <RowNumber>
    Then I should be successfully logged in

  Examples:
      | SheetNumber | RowNumber |
      | 1           | 1         |
     
 @search
  Scenario Outline: Search for a course
    Given I am on the homepage
    When I enter a search query for "<Course>"
    Then relevant search results should be displayed

    Examples:
      | Course      |
      | Python      |
      
      
 @enrollment
  Scenario Outline: Validate course enrollment process
    Given I am on a course page
   When I enroll in the course with data from Excel at Sheet <SheetNumber> and Row <RowNumber>
   Then I should be successfully enrolled

Examples:
  | SheetNumber | RowNumber |
  | 2           | 1         |
   
      