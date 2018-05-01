package friends;

/*
 * FriendsDAO class (Data Access Object)
 * Created by Kyle Rickets on 4/24/2018
 * 
 */

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Main;
import posts.Post;
import profile.Profile;
import util.DBUtil;

public class FriendsDAO
{
	public static ObservableList<Profile> searchPeople(String username, String fName, String lName){
		String selectStmt = "SELECT * FROM userProfiles, friendsRelation WHERE userProfiles.username='" + username + "' OR userProfiles.FirstName='" + fName 
				+ "' OR userProfiles.LastName='" + lName + "' AND NOT(friendsRelation.userID="+Main.userID+" OR friendsRelation.friendsID= " + Main.userID +")";
		//"SELECT * FROM userProfiles WHERE ...; match on either 3 criteria and exclude all profiles that have a friendRelation with the user
		
		try {
			ResultSet rsPeople;
			rsPeople = DBUtil.dbExecuteQuery(selectStmt);
			ObservableList<Profile> peopleList = getProfileList(rsPeople);
			return peopleList;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static ObservableList<Profile> getProfileList(ResultSet rs) {
		ObservableList<Profile> profileList = FXCollections.observableArrayList();
		try {
			while(rs.next()) {
				Profile profile = new Profile();
				//These labels correspond directly to what they are in the SQL database for userProfiles
				profile.setUsername(rs.getString("username"));
				profile.setFirstName(rs.getString("FirstName"));
				profile.setLastName(rs.getString("LastName"));
				profile.setEmail(rs.getString("Email"));
				profile.setBirthday(rs.getDate("DOB").toString());
				profile.setSecurityQuestion(rs.getString("SecurityQuestion"));
				profile.setSecurityAnswer(rs.getString("SecurityAnswer"));
				profile.setPhoneNumber(rs.getString("PhoneNumber"));
				profile.setOccupation(rs.getString("Occupation"));
				profile.setSchool(rs.getString("School"));
				profile.setStatus(rs.getString("Status"));
				
				//Add the profile to the list of profiles
				profileList.add(profile);
			}
			return profileList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static ObservableList<Profile> getFriends(int userID)
	{
		//This is pretty much the same code as searchPeople/3 but with a different selectStmt
		
		//friendsRelation.userID = userID AND friendsRelation.friendsID = userProfiles.userID;

		String selectStmt = "SELECT * FROM userProfiles, friendsRelation WHERE friendsRelation.userID= " + userID + " AND (userProfiles.userID= friendsRelation.friendsID)";
		//"SELECT * FROM userProfiles, friendsRelation WHERE (friendsRelation.userID = userID AND friendsRelation.friendsID = userProfiles.userID)...; Select all user profiles (userProfiles) that are friends (friendsRelation) of user (userID)
		try {
			ResultSet rsFriends = DBUtil.dbExecuteQuery(selectStmt);
			ObservableList<Profile> friendsList = getProfileList(rsFriends);
			System.out.println("There were " + friendsList.size() + " in the friendList: " + friendsList.toString());
			return friendsList;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	//Delete selected friend
	public static void deleteFriend(int userID, int friendID) {
		
		String updateStmt = "DELETE FROM friendsRelation WHERE userID=" + userID + " AND friendsID=" + friendID;
		//DELETE from friendRelation\n
		//WHERE friendID is ____ and userID is userID ;\n
		try {			
			DBUtil.dbExecuteUpdate(updateStmt);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return;
	}
	
	public static void addFriend(Profile friend, int userID) {
		int friendID = friend.getUserID();
		String queryStmt = "SELECT TOP 1 * FROM Table ORDER BY ID DESC";
		String updateStmt = "";
//		"INSERT INTO friendRelation(relationID, userID, friendID)\n" +
//				"VALUES\n" +
//				"("+ relationID +", '" + userID + "', '" + friendID + "');\n";
		try {
			ResultSet rsRelation = DBUtil.dbExecuteQuery(queryStmt);
			int relationID = rsRelation.getInt("friendsRelationID");
			updateStmt = String.format("INSERT INTO friendsRelation(friendsRelationID, userID, friendsID)\n" + "VALUES " + "('%d', '%d', '%d');", relationID+1, userID, friendID);
			DBUtil.dbExecuteUpdate(updateStmt);
			//updateStmt = String.format("INSERT INTO friendsRelation(friendsRelationID, userID, friendsID)\n" + "VALUES " + "('%d', '%d', '%d');"arg0, arg1)
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return;
	}
}
