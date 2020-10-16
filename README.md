# Cloud Native Fundamental Project

### Resources:
* JIRA Board: https://qatraineejaguila.atlassian.net/secure/RapidBoard.jspa?rapidView=1&projectKey=PCI&view=planning&selectedIssue=PCI-7&epics=visible&issueLimit=100

## Contents
* [Brief](#brief)
   * [Additional Requirements](#additional-requirements)
   * [My Approach](#my-approach)
* [Architecture](#architecture)
   * [Database Structure](#database-structure)
* [Project Tracking](#project-tracking)
* [Risk Assessment](#risk-assessment)
* [Testing](#testing)
* [Front-End Design](#front-end-design)
* [Known Issues](#known-issues)
* [Future Improvements](#future-improvements)
* [Authors](#authors)

## Brief
The brief provided to us for this project sets the following out as its overall objective:
"To create a CRUD application with the utilisation of supporting tools, methodologies and technologies that encapsulate all core modules covered during training."
In layman's terms this means our applications must use technologies that we have learned over the course of the training and incorporate create, read, update and delete functions as part of its core functionality. 

### Additional Requirements
In addition to what has been set out in the brief, I am also required to include the following:
* A JIRA Kanban board
* A relational database using H2, hosted on a GCP SQL Server if possible
* A detailed Risk Assessment and clear documentation of the design phase describing architecture
* A functional application created in Java, following best practices which must meet the requirements set on my JIRA board
* Test suites for the application, which will include automated tests for validation of the application
* A front-end websitd and integrated API
* Code integrated into a Version Control System

### My Approach
To achieve this, I have decided to produce a simple stargazing companion app that must allow the user to do the following:
* Create a user account (satisfying 'Create') that stores:
   * *Account Number*
   * *First and Last Name*
   * *Password*
* Make payments to other users (satisfying 'Create') with the following information:
   * *Amount* of the payment
   * *recipient account Number* of the payment 
* View and update user account details (satisfies 'Read' and 'Update')
* Delete their account (satisfies 'Delete')

Additionally, I would like to allow the user to:
* View all payments they have made in the past

## Architecture
### Database Structure
Pictured below is an entity relationship diagram (ERD) showing the structure of the database.


![ERD][erd1]



As shown in the ERD, the app models a one-to-many relationship between User entities and Payment entities using an association table. This allows the user to create Payments associated with their user ID.


## Project Tracking
JIRA was used to track the progress of the project (pictured below). 
Here's the link to the board:https://qatraineejaguila.atlassian.net/jira/software/projects/PO/boards/2

![trello][trello]


## Risk Assessment

![RiskAssessment][riskassessment]

## Testing
JUnit is used to run unit tests on the app. These are designed to assert that if a certain function is run, the output should be a known value.

The ecl emma extension to Spring produces a coverage report to show how much of the code in the app has been successfully tested. This is shown below

![coverage][coverage]

## Front-End Design
The front-end of the app is built using HTML, CSS and Javascript with an integrated API.

When the user navigates to the URL, they are directed to the home page:

![homeloggedout][homeloggedout]

Users then sign up on the associated page:

![signup][signup]

Once signed up, they then receive an account number which they use to sign in and log in to see the account page:

![login][login]


## Known Issues
There are a few bugs with the current build of the app:
* Currently the data transfer objects used to return the same attributes as the objects themselves. This includes the password, which is a security risk.
* Passwords are stored as cleartext. I would like to hash these to ensure logins are secure.
* Random account number generators and login functions are not tested due to time constraints.

## Future Improvements
There are a number of improvements I would like to implement (outside of current bugs):
* The database should be hosted on a GCP SQL Server
* I would like for the users to be able to display all payments, and possibly cancel them
* I would also like users to be able to record more information like an email address, phone number and a username
* I want a pop up to show up for the delete account feature, asking whether users are sure they want to delete their account.

## Authors
Juan Carlos Aguila

[erd1]: https://i.imgur.com/bHa7pFw.png
[riskassessment]: https://i.imgur.com/p4ZAufX.png
[coverage]: https://i.imgur.com/fvW18vg.png
[trello]: https://i.imgur.com/eNVwpFG.png
[homeloggedout]: https://i.imgur.com/Ol4O1wG.png
[signup]: https://i.imgur.com/XyGMkmg.png
[login]: https://i.imgur.com/gmBxlRT.png

