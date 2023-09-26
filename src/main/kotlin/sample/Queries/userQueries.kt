package sample.Queries

const val readUserQuery = "SELECT * FROM users"

const val readUserUpdateQuery = "SELECT * FROM users Where id!=?"

const val createUserQuery = "INSERT INTO users (fullname, username, password, address) VALUES (? ,? ,? ,?)"

const val deleteUserQuery = "DELETE FROM users WHERE id=?"

const val updateUserQuery = "UPDATE users SET fullname=?, username=?, password=?, address=? WHERE id=?"