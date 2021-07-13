== TO DO == 

- Email webhook 
- See opportunity for refactoring
- Test coverage for the system core
- Controllers + tests for them
- Manual testing 
- README

# Introduction 

===
!!! NOTE !!!

This application contains hardcoded API keys and login/password details for ease of testing. 
This is not something to be done in a production environment in any circumstances.
It is done in this app for ease of testing by the reviewers of the challenge and special 
"throwaway" accounts are used. 

===

This application will allow users to send & receive messages via Sms and Email.

Sending and receipt of SMS is done via integration with Twilio. (https://www.twilio.com/)
SMS messages are sent using Twilio SDK. 
SMS messages are received by using a webhook which parses out the necessary values from the 
request Twilio sends to the webhook. The messages are forwarded to the right user based on the 
incoming phone number.


To test & demonstrate email functionality Mailtrap is used. 
Once a message is sent to a specific user by the application it can be seen in the relevant Mailtrap 
account. Credentials to check this account are provided in the notes below.

To receive notification of an incoming email a webhook is also provided.
To test this functionality you can set up Gmail account and configure a webhook for it using 
automate.io (https://automate.io/). The webhook expects the incoming message to follow a 
specific JSON schema.


# Architecture
The app has a standard 3 layered architecture.

The main services are located in the 'service' package.
The key service is "MessagingService" it is used to send messages from users to their contants.
It also allows users to receive messages from their contacts.

Controllers act as thin wrappers for the services.

Key flows through the application are covered by unit/integration tests.

# Placeholder replacement 
Placeholders are called "Tokens" within the application
Placeholder replacement is done by the "TokenReplacementService".
Replacement is done exactly before adding a message to the send queue. 
This is done from performance optimisation standpoint, because token replacement 
uses regular expressions and in general is a very expensive operation.

Therefore, if we need to handle message resend/retry trying to do substitution
each time before we send out the message can be an expensive operation. 


# Data model 
The key entities are located in the 'entity package'. 
Here is a brief description of each.

User -> User of the system. Follows the standard schema of spring security to make configuring
them easier. 

UserDetails -> Contacts details for a user such as phone/email, etc.

Contact -> Each user has zero or more contacts to whom he can send/receive messages.

Message -> The object, which represents a message, which is sent/received by a user. 

# Notes

Mailtrap account credentials: 