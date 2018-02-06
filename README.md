# Labs
----- 
**Clients tab:**
- Save accounts to client file [done]
- Load a particular client file when client logs in
- Load all client files when bank employee logs in [done]
- Update: Employee will load list of all clients. Then he/she will search for a client. We can load a particular client file in that moment because the first name and surname will be specified and it will make it easier for us. [done]

**Transactions tab:**
- Log deposit and withdrawal transactions in the GUI
- Save these transactions to customer file
- Load these transactions from the customer file

- Total count of transactions, deposit/withdrawn type of transaction, who?, bank name, account, balance... what else?
- Two versions of the tab:
	- For user - see transactions for his/her accounts -> related to Accounts class
	- For bank employees - see all accounts and their transactions
- Ability to approve transactions for bank employees? How to implement this? Sockets?

**Immediate tasks check:**
- Fix attributes in ClsAccount
- Fix attributes to DATEs from STRINGs

-----

### Further tasks:
4. Fix access to tabs depending on the use case diagram, fix login and register buttons accordingly
5. Enhance transactions tab and implement full functionality
6. Person class <=> customer class ?
	- Customer is a person. Customer will inherit person's methods?
7. Check default values for account, person and customer classes, fill them into the class diagram.
8. All account classes will probably have an error where variable "days" is used. Fix as required.
9. Implement Date variable in customer, person, transaction and account classes.
10. After accounts and transactions are done, study factories and sockets.
11. Use case descriptions for each use case in the use case diagram.
	- Admins can create accounts for customer, save to file, calculate charges, deposit monthly and yearly interest onto accounts.
12. Sequence diagrams for each use case in the use case diagram.

99. XML Implementation of the files // possibly left for the iteration 3:
    - We will need to dynamically create XML files by XML "creator". We can use XML for iterations to sort out our headaches.
    - How to: https://www.youtube.com/watch?v=H-aTpt4NG-s
    
-----

### Possible error locations:
- Class   |   Line

-----
**Markdown cheatsheet:**
https://github.com/adam-p/markdown-here/wiki/Markdown-Cheatsheet#h3

-----
### Use case diagram summary:

**Bank advisor:**
- Login
- Display Head office details
- Display all clients information
- Display all client accounts details
- Search for client
- Display particular client's information
- Display particular client's account details
- Load branches from file
- Display subdepartments

**Bank manager:**
- All use cases

**Bank administrator:**
- Login
- Register
- Display Head office details
- Display person details
- Display person address
- Edit client information
- Load clients from file
- Display all clients information
- Display all client accounts details
- Search for client
- Display particular client's information
- Display particular client's account details
- Load branches from file
- Display subdepartments
- Show account summary
- Display transactions
- Generate statement
- Deposit
- Withdraw

**Bank client:**
- Login
- Display Head office details
- Display Head office address
- Display person details
- Display person address
- Display particular client's information
- Display particular client's account details
- Show account summary
- Display transactions
- View statement