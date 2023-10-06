package sample.Queries

// query for getting all data from users table
const val readUserQuery = "SELECT * FROM user_account, user_credential WHERE user_account.userid = user_credential.userid"

// query for getting data from userlogin
const val getUserLogin = "SELECT * FROM user_account WHERE useremail = ?"

// query for getting all data from users table except from the id that not equal to said parameter
const val readUserUpdateQuery = "SELECT * FROM user_account, user_credential Where user_account.userid = user_credential.userid and user_account.userid != ?"

// query to store all input data into users table
const val createUserAccountQuery = "INSERT INTO user_account (userid, useremail, userpassword) VALUES (? ,? ,?)"
const val createUserCredentialQuery = "INSERT INTO user_credential (userid, userfullname, userphone, useraddress) VALUES (? ,? ,?, ?)"

// query to delete data from users
const val deleteUserQuery = "Delete FROM user_account using user_credential where user_credential.userid = user_account.userid and user_credential.userid = ?;" +
        "Delete FROM user_credential where userid = ?"

// query for updating data from the users
const val updateUserQuery = "UPDATE user_account SET useremail = ?, userpassword = ? WHERE userid = ?;" +
        "UPDATE user_credential SET userfullname = ?, userphone = ?, useraddress = ? WHERE userid = ?"