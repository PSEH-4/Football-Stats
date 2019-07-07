package com.football.api.standings.controller;

import java.util.HashMap;
import java.util.StringTokenizer;

import javax.print.attribute.HashAttributeSet;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.football.api.standings.controller.model.Competitions;
import com.football.api.standings.controller.model.Countries;
import com.football.api.standings.controller.model.StandingsResponse;

@RestController
public class StandingDetailsController {
	
	String countryName="England";
	String leagueName="Premier League";
	String teamName="Manchester City";
	
	@GetMapping("/footstats/standings/{countryName}/{leagueName}/{teamName}")
	public HashMap<String,String> getTeamStadings (@PathVariable("countryName") String countryName , @PathVariable("leagueName") String leagueName, @PathVariable("teamName") String teamName) {
		HashMap<String,String> result = new HashMap<String, String>();
		
		try {
			
			if(countryName!=null && leagueName!=null && teamName!=null) {
				
				String countryID = getCountryIDFromCountryName(countryName);
				result.put(countryID,countryName);
				System.out.println("countryID >>>> :" + countryID);
				
				String leagueId = getLeagueId(countryID,leagueName);
				result.put(leagueId,leagueName);
				System.out.println("leagueId >>>> :" + leagueId);
				
				String teamIdLeaguePos = getStandingsDetails(leagueId,countryName,teamName);
				System.out.println("teamIdLeaguePos >>>> :" + teamIdLeaguePos);
				
				String[] str = teamIdLeaguePos.split("-");
				result.put(str[0],teamName);
				result.put("overall_league_position",str[1]);
				
			}else {
				result.put("InputValidationError", "Please Specify Country Name , League Name and Team Name");
			}
			
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
		
	}

	//@GetMapping("/getCountryId")
	//String countryName param
	public String getCountryIDFromCountryName(String countryName) { 
		//String countryName="England";
		System.out.println("Getting country Id from Country Name");
		String countryID = "";
		
		try {
			
			HttpHeaders headers = new HttpHeaders();
	    	headers.setContentType(MediaType.APPLICATION_JSON);
			RestTemplate restTemplate = new RestTemplate();
			
			String URL = "https://apiv2.apifootball.com/?action=get_countries&APIkey=9bb66184e0c8145384fd2cc0f7b914ada57b4e8fd2e4d6d586adcc27c257a978";
			
			HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
			ResponseEntity<Countries[]> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, requestEntity, Countries[].class);
			Countries[] countrydetails = responseEntity.getBody();
			System.out.println("countrydetails :" + countrydetails);
			for(Countries country : countrydetails) {
				if(country.getCountry_name().equalsIgnoreCase(countryName)) {
					countryID = country.getCountry_id();
				}
			}
			System.out.println("countryID : " + countryID);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return countryID;
	}
	
	//String countryId , LeagueName - param
	//@GetMapping("/getId")
	public String getLeagueId(String countryID,String leagueName) {
		System.out.println("getting League Id");
		
		//String leagueName="Isthmian League Premier Division";
		//String countryID = "41";
		String LeagueId = "";
		
		try {
			
			HttpHeaders headers = new HttpHeaders();
	    	headers.setContentType(MediaType.APPLICATION_JSON);
			RestTemplate restTemplate = new RestTemplate();
			
			String url = "https://apiv2.apifootball.com/?action=get_leagues&country_id=&APIkey=9bb66184e0c8145384fd2cc0f7b914ada57b4e8fd2e4d6d586adcc27c257a978";
			
			UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromUriString(url);

			urlBuilder.replaceQueryParam("country_id", countryID);

			String resultURL = urlBuilder.build().toUriString();
			
			System.out.println("Result url :" + resultURL);
			
			HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
			ResponseEntity<Competitions[]> responseEntity = restTemplate.exchange(resultURL, HttpMethod.GET, requestEntity, Competitions[].class);
			Competitions[] Competitionsdetails = responseEntity.getBody();
			
			for(Competitions compt : Competitionsdetails) {
				if(compt.getCountry_id().equalsIgnoreCase(countryID) && compt.getLeague_name().equalsIgnoreCase(leagueName)) {
					LeagueId = compt.getLeague_id();
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return LeagueId;
		
	}
	
	//String LeagueID ,country_name , team_name as param 
	//@GetMapping("/standings")
	public String getStandingsDetails (String LeagueId,String country_name,String team_name) {
		//String LeagueId = "148";
		//String country_name = "England";
		//String team_name = "Manchester City";
		
		String teamId = "";
		String overall_league_position = "";
		StringBuffer teamIdLeaguePos = new StringBuffer();
		
		try {
			
			HttpHeaders headers = new HttpHeaders();
	    	headers.setContentType(MediaType.APPLICATION_JSON);
			RestTemplate restTemplate = new RestTemplate();
			
			String url = "https://apiv2.apifootball.com/?action=get_standings&league_id=0&APIkey=9bb66184e0c8145384fd2cc0f7b914ada57b4e8fd2e4d6d586adcc27c257a978";
			
			UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromUriString(url);

			urlBuilder.replaceQueryParam("league_id", LeagueId);

			String resultURL = urlBuilder.build().toUriString();
			
			System.out.println("Result url :" + resultURL);
			
			HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
			
			ResponseEntity<StandingsResponse[]> responseEntity = restTemplate.exchange(resultURL, HttpMethod.GET, requestEntity, StandingsResponse[].class);
			StandingsResponse[] StandingsResponsedetails = responseEntity.getBody();
			
			for(StandingsResponse standings : StandingsResponsedetails) {
				if(standings.getLeague_id().equalsIgnoreCase(LeagueId) && standings.getCountry_name().equalsIgnoreCase(country_name) && standings.getTeam_name().equalsIgnoreCase(team_name)) {
					teamId = standings.getTeam_id();
					overall_league_position = standings.getOverall_league_position();
				}
			}
			System.out.println(teamId);
			System.out.println(overall_league_position);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		teamIdLeaguePos.append(teamId).append("-").append(overall_league_position);	
		return teamIdLeaguePos.toString();
	}
}
