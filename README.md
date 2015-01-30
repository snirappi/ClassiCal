# Problem Statement

There is no intuitive and ubiquitous app for students to manage their time in and out of class collaboratively with their peers. A lack of community is a massive problem that plagues collegiate academic classes. To solve this, we are building a crossplatform service to connect Purdue students to each other based on what classes they are in. This service will have scheduling, group messaging, and a forum to provide a wide variety of options for students to connect with each other.

# Background Information

At a school of 40,000+ Undergraduate students like Purdue, knowing no one in a class can feel isolating and is all too common. This disjoint disposition from a class can make it awkward and near impossible to ask questions or set up study groups with peers. For the course of CS307 we have set the domain of our app to encompass all students at Purdue University. 
Currently myPurdue’s scheduler works well enough as a way to view your schedule, but offers nothing for class interaction. As well current instant messaging solutions lack a sense of community for students within the same classes as the messengers target users who know each other as more than classmates. Piazza attempts to offer a solution, but, it does not foster communication from peer to peer since Piazza was established for questions towards the professor and Teaching Assistants. Furthermore, Piazza must be created by the classes’ administration, resulting in most classes not having any form of online help available. 
We plan to address these limitations by creating a web, Android, and iOS application that will take schedule information from a student’s Purdue Career Account, and give them access to chat rooms for each of their classes. There will be 2 sides to the communication: Real time chatting, and forums. The real time chatting is intended to foster a sense of community in each class, along with providing answers to quick questions that don’t necessitate a full forum thread. The forum will be intended to answer questions that students have for each other, pertaining to the homework, studying, or even general opinions about the class as a whole.

# Requirements 

Functional Requirements
Web:
As a user, I would like to have a secure login to this app using myPurdue account
As a user, I would like to view my class schedule in a quick and concise manner 
As a user, I would like to be able add and remove classes from my class schedule 
As a user, I would like to be able to add and remove events from my class schedule
As a user, I would like to edit event information pertaining to location and time
As a user, I would like to be able to ask questions about the courses on the forum
As a user, I would like to be able to vote on better or worse answers in forum topics
As a user, I would like to have the ability to chat with classmates about both course related and unrelated topics in real time.
As a user, I would like to post anonymously if I don’t feel comfortable speaking about my issue.
As a user, I would like to be able to add/participate with the pages of classes that I’m not currently taking.
Android:
As a user, I would like to have a secure login to this app using myPurdue account
As a user, I would like to view my class schedule in a quick and concise manner 
As a user, I would like to be able add and remove classes from my class schedule 
As a user, I would like to be able to add and remove events from my class schedule
As a user, I would like to edit event information pertaining to location and time
As a user, I would like to be able to ask questions about the courses on the forum
As a user, I would like to be able to vote on better or worse answers in forum topics
As a user, I would like to have the ability to chat with classmates about both course related and unrelated topics in real time.
As a user, I would like to post anonymously if I don’t feel comfortable speaking about my issue.
As a user, I would like to be able to add/participate with the pages of classes that I’m not currently taking.
iOS:
As a user, I would like to have a secure login to this app using myPurdue account
As a user, I would like to view my class schedule in a quick and concise manner 
As a user, I would like to be able add and remove classes from my class schedule 
As a user, I would like to be able to add and remove events from my class schedule
As a user, I would like to edit event information pertaining to location and time
As a user, I would like to be able to ask questions about the courses on the forum
As a user, I would like to be able to vote on better or worse answers in forum topics
As a user, I would like to have the ability to chat with classmates about both course related and unrelated topics in real time.
As a user, I would like to post anonymously if I don’t feel comfortable speaking about my issue.
As a user, I would like to be able to add/participate with the pages of classes that I’m not currently taking.
Other:
As a teaching assistant/Professor, I would like to students to be able to report offensive or rule-breaking posts
As a teaching assistant/Professor, I would like to contact individual students through the application as a TA/Professor




# Non-Functional Requirements

	Availability: While not immediately on our plate, the application and server should be designed with expandability to non-Purdue universities. Should the product be well received at Purdue, it shouldn’t be prohibitively difficult to add support for another school’s account system.
	Performance: Users demand quick server response times in order to make the app feel ‘realtime’. Chat/Forum update times can be fairly slow, as the user is unaware of when messages are actually sent. However, load times must be kept quick in order to prevent frustrating or boring the user.
	Security. Logins are powered by Purdue Career accounts, which will have value to hackers. The server must be designed to protect the user account information. 
	Stability: The apps and server must be reliable to a degree, however, as there is no critical or time sensitive data that rely on this service, some downtime can be acceptable.
	Portability: The user should be able to post a message on the mobile application with classmates, and also be able to see/respond to that message from another device as the same user simultaneously.
	Cheating prevention: Decisions must be made to prevent cheating being facilitated, while not preventing students from talking to each other freely.
