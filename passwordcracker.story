Narrative:
In order to crack a password
As a user
I want to run the program without errors

Scenario: Input file data as dictionary
Given a directory name
When the files are present 
Then add the content of the files in that directory(no subdir for the prototype) to the dictionary

Scenario: change target file for cracking
Given a target file name
When the file is present 
Then the passwords are checked against that file

Scenario: logging success and failure
Given a logger
When the program has finished executing
Then write a report of success/failure to a file