package sample.controllers

import sample.config.DBConfig
import sample.models.*
import sample.Queries.*

class nestedController {
    fun insertperson (userId: Int, userFullName: String, companyName: String, jobTitle: String, companyAddress: String, yearsExperience: String){
        val personQuery = DBConfig().connect().prepareStatement(insertQuery)

        // declaring value in every parameter of the query
        personQuery.setInt(1, userId)
        personQuery.setString(2, userFullName)
        personQuery.setInt(3, userId)
        personQuery.setString(4, companyName)
        personQuery.setString(5, jobTitle)
        personQuery.setString(6, companyAddress)
        personQuery.setString(7, yearsExperience)

        personQuery.executeUpdate()
    }

    fun getParentData(): MutableList<userDetail>{
        val parentdata = mutableListOf<userDetail>()
        val parentquery = DBConfig().connect().prepareStatement(getparent)
        val parentresult = parentquery.executeQuery()

        while (parentresult.next()) {
            // getting the value of the id column
            val userId = parentresult.getInt("userid")
            // getting the value of the name column
            val fullname = parentresult.getString("fullname")


            parentdata.add(userDetail(userId,fullname,getBabyData(userId)))
        }
        // the query is executed and results are fetched
        return parentdata
    }

    fun getBabyData(id: Int) : MutableList<getUserTestWorkExperience>{
        val babydata = mutableListOf<getUserTestWorkExperience>()
        // calling the query
        val babyquery = DBConfig().connect().prepareStatement(getbaby)
        babyquery.setInt(1, id)
        val babyresult = babyquery.executeQuery()
        if (babyresult.next()){
            val recordid:Int = babyresult.getInt("recordid")
            // getting the value of the name column
            val userid = babyresult.getInt("userid")
            // getting the value of the name column
            val companyName = babyresult.getString("companyname")
            val jobTitle = babyresult.getString("jobtitle")
            val companyAddress = babyresult.getString("companyaddress")
            val yearsExperience = babyresult.getString("yearsofexperience")

            babydata.add(getUserTestWorkExperience(recordid,userid,companyName,jobTitle,companyAddress,yearsExperience))
        }

        return babydata
    }
}