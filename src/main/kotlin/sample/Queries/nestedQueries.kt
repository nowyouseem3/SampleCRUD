package sample.Queries

// query for inserting person's data
const val insertQuery = "INSERT into person (userid, fullname) VALUES (?, ?);" +
        "INSERT into work_data (userid, companyname, jobtitle, companyaddress, yearsofexperience) VALUES (?, ?, ?, ?, ?)"

const val getparent = "SELECT * FROM person "
const val getbaby = "SELECT * FROM work_data WHERE userid = ?"