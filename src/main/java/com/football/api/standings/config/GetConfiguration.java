package com.football.api.standings.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GetConfiguration {
	
	@Value("${get_countries_url}")
	private String getCountriesUrl;
	
	@Value("${get_leagues_url}")
	private String getleaguesurl;
	
	@Value("${get_standings_url}")
	private String getstandingsurl;

	public String getGetCountriesUrl() {
		return getCountriesUrl;
	}

	public void setGetCountriesUrl(String getCountriesUrl) {
		this.getCountriesUrl = getCountriesUrl;
	}

	public String getGetleaguesurl() {
		return getleaguesurl;
	}

	public void setGetleaguesurl(String getleaguesurl) {
		this.getleaguesurl = getleaguesurl;
	}

	public String getGetstandingsurl() {
		return getstandingsurl;
	}

	public void setGetstandingsurl(String getstandingsurl) {
		this.getstandingsurl = getstandingsurl;
	}
	
	

}
