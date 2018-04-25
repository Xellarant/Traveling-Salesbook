package profile;
/*
 * Profile database access object
 * 
 */
import java.sql.ResultSet;
import java.sql.SQLException;

import util.DBUtil;
import static util.DataUtil.boolToInt;

public class ProfileDAO {
	//select user profile
	public static Profile searchProfile(String userID){
		String selectStmt = "SELECT * FROM userProfiles WHERE userID=" + userID;
		try {
			ResultSet rsProfile;
			rsProfile = DBUtil.dbExecuteQuery(selectStmt);
			Profile profile = getProfile(rsProfile);
			return profile;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//Store profile data from database in an Profile object
	private static Profile getProfile(ResultSet rs) {
		Profile profile = new Profile();
		try {
			if(rs.next()) {
				profile.setUsername(rs.getString("username"));
				profile.setFirstName(rs.getString("FirstName"));
				profile.setLastName(rs.getString("LastName"));
				profile.setEmail(rs.getString("email"));
				profile.setBirthday(rs.getString("DOB"));
				profile.setSecurityQuestion(rs.getString("SecurityQuestion"));
				profile.setSecurityAnswer(rs.getString("SecurityAnswer"));
				profile.setPhoneNumber(rs.getString("PhoneNumber"));
				profile.setOccupation(rs.getString("Occupation"));
				profile.setSchool(rs.getString("School"));
				profile.setStatus(rs.getString("Status"));
			}
			return profile;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	//select all privacy
	public static ProfilePrivacy searchProfilePrivacy(String userID){
		String selectStmt = "SELECT * FROM userPrivacyPreferences WHERE userID=" + userID;
		try {
			ResultSet rsPrivacy;
			rsPrivacy = DBUtil.dbExecuteQuery(selectStmt);
			ProfilePrivacy profilePrivacy = getProfilePrivacy(rsPrivacy);
			return profilePrivacy;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//Store user privacy reference data from database in an ProfilePrivacy object
	private static ProfilePrivacy getProfilePrivacy(ResultSet rs) {
		ProfilePrivacy profilePrivacy = new ProfilePrivacy();
		try {
			if(rs.next()) {
				profilePrivacy.setFirstName(rs.getBoolean("showFirstName"));
				profilePrivacy.setLastName(rs.getBoolean("showLastName"));
				profilePrivacy.setEmail(rs.getBoolean("showEmail"));
				profilePrivacy.setBirthday(rs.getBoolean("showBirthday"));
				profilePrivacy.setPhoneNumber(rs.getBoolean("showPhoneNumber"));
				profilePrivacy.setOccupation(rs.getBoolean("showOccupation"));
				profilePrivacy.setSchool(rs.getBoolean("showSchool"));
				profilePrivacy.setStatus(rs.getBoolean("showStatus"));
				profilePrivacy.setPosts(rs.getBoolean("showPosts"));
				profilePrivacy.setFriends(rs.getBoolean("showFriends"));
			}
			return profilePrivacy;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	//edit user profile
	public static void editProfile(Profile profile) {
		String updateStmt = 
				"UPDATE UserProfiles\n" +
						"SET username = '" + profile.getUsername() 
						+"', FirstName = '" + profile.getFirstName()
						+"', LastName = '" + profile.getLastName() 
						+"', Email = '" + profile.getEmail()
						+"', DOB = '" + profile.getBirthday()
						+"', PhoneNumber = '" + profile.getPhoneNumber()
						+"', Occupation = '" + profile.getOccupation()
						+"', School = '" + profile.getSchool()
						+"', Status = '" + profile.getStatus()
						+ "'\n"
						+ "WHERE userID = " + profile.getUserID() +";\n";
		try {
			DBUtil.dbExecuteUpdate(updateStmt);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	//edit user privacy reference (hide or show)
	public static void editUserPrivacy(ProfilePrivacy profilePrivacy) {
		String updateStmt = 
				"UPDATE UserPrivacyPreferences\n" +
						"SET showPosts = '" +  boolToInt(profilePrivacy.getPosts()) 
						+"', showFirstName = '" +  boolToInt(profilePrivacy.getFirstName())
						+"', showLastName = '" +  boolToInt(profilePrivacy.getLastName()) 
						+"', showEmail = '" +  boolToInt(profilePrivacy.getEmail())
						+"', showBirthday = '" +  boolToInt(profilePrivacy.getBirthday())
						+"', showPhoneNumber = '" +  boolToInt(profilePrivacy.getPhoneNumber())
						+"', showOccupation = '" +  boolToInt(profilePrivacy.getOccupation())
						+"', showSchool = '" +  boolToInt(profilePrivacy.getSchool())
						+"', showStatus = '" +  boolToInt(profilePrivacy.getStatus())
						+"', showFriends = '" +  boolToInt(profilePrivacy.getFriends())
						+ "'\n"
						+ "WHERE userID = " + profilePrivacy.getUserID() +";\n";
		try {
			DBUtil.dbExecuteUpdate(updateStmt);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	


}
