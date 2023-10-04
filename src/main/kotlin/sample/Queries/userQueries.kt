package sample.Queries

// query for getting all data from users table
const val readUserQuery = "SELECT * FROM user_account, user_credential WHERE user_account.userid = user_credential.userid"

// query for getting data from userlogin
const val getUserLogin = "SELECT * FROM users WHERE username = ?"

// query for getting all data from users table except from the id that not equal to said parameter
const val readUserUpdateQuery = "SELECT * FROM users Where id!=?"

// query to store all input data into users table
const val createUserAccountQuery = "INSERT INTO user_account (userid, useremail, userpassword) VALUES (? ,? ,?)"
const val createUserCredentialQuery = "INSERT INTO user_credential (userid, userfullname, userphone, useraddress) VALUES (? ,? ,?, ?)"

// query to delete data from users
const val deleteUserQuery = "DELETE FROM users WHERE id=?"

// query for updating data from the users
const val updateUserQuery = "UPDATE users SET fullname=?, username=?, password=?, address=? WHERE id=?"