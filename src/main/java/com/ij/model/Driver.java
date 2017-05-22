package com.ij.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * @author Indy
 *
 */
@Document(collection = "drivers")
public class Driver implements UserDetails{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6508170617977087424L;
	@Id
    private String id;
    private String username;
    private String password;
    @Indexed
    private String userId = null;
    private ArrayList<Journey> journeys = null;
    private String[] roles = new String[2];
 

    public Driver() {

        journeys = new ArrayList<>();
    }

    public Driver(String username, String password) {
		this.username = username;
		this.password = password;
		journeys = new ArrayList<>();
	}




	public String getUsername() {
		return username;
	}




	public void setUsername(String username) {
		this.username = username;
	}




	public String getPassword() {
		return password;
	}




	public void setPassword(String password) {
		this.password = password;
	}




	public void setId(String id) {
		this.id = id;
	}




	public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }



    public ArrayList<Journey> getJourneys() {
		return journeys;
	}

	public void setJourneys(ArrayList<Journey> journeys) {
		this.journeys = journeys;
	}
	

	public String[] getRoles() {
		return roles;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}

	@Override
    public String toString() {
        return "Driver{" +
                "userId='" + userId + '\'' +
                ", journeyList=" + journeys +
                '}';
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}


}
