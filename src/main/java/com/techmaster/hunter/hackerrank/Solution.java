package com.techmaster.hunter.hackerrank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONException;
import com.google.gson.GsonBuilder;

public class Solution {
	
	public static String findNumber(int[] arr, int k) {
        if ( null != arr && arr.length > 0 ) {
            for ( int i=0; i<arr.length; i++ ) {
                int val = arr[i];
                if ( val == k ) {
                    return "YES";
                }
                if ( i == arr.length - 1 ) {
                    return "NO";
                }
            }
        }
        return null;
    }
	
	public static int[] oddNumbers(int l, int r) {
		List<Integer> oddIndices = new ArrayList<>();
		for ( int i = l; i < r; i++ ) {
			if ( i % 2 != 0 ) {
				oddIndices.add(i);
			}
		}
		int[] arr = new int[ oddIndices.size() ];
		for( int i = 0; i < oddIndices.size(); i++ ) {
			arr[i] = oddIndices.get(i);
		}
		return arr;
    }
	
	private static HttpURLConnection getConnection( String subStr, int pageNumber )  {
		try {
			String baseURL = "https://jsonmock.hackerrank.com/api/movies/search/?Title=" + subStr + "&page=" + pageNumber;
			URL urlObj = new URL(baseURL);
			HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			return con;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static Payload getJSONObjFromConn( HttpURLConnection conn ) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader( conn.getInputStream() ) );
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			System.out.println(response);
			Payload payLoad = new GsonBuilder().create(). fromJson(response.toString(), Payload.class);
			return payLoad;
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static void addTotitles( String [] titles, Payload payload, int pageNumber, int perPage ) {
		List<Movie> movies = payload.getData();
		for ( int i = 0 ; i < movies.size(); i++ ) {
			Movie movie = movies.get(i);
			titles[ (pageNumber - 1) * perPage + i ] = movie.getTitle();
		}
	}
	
	public static String[]  getMovieTickets( String subStr ) {
		try {
			
			HttpURLConnection firstConn = getConnection( subStr, 1);
			int responseCode = firstConn.getResponseCode();
			Payload firstPayLoad = null;
			String[] titles = null;
			int total = 0;
			int totalPages = 0;
			int perPage = 0;
			
			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				firstPayLoad = getJSONObjFromConn(firstConn);
				if ( firstPayLoad != null ) {
					total = firstPayLoad.getTotal();
					totalPages = firstPayLoad.getTotal_pages();
					titles = new String[total];
					perPage = firstPayLoad.getPer_page();
					addTotitles(titles, firstPayLoad, 1, perPage );
				}
			} else {
				System.out.println("GET request not worked");
			}
			
			for ( int i = 2; i <= totalPages; i++ ) {
				HttpURLConnection conn = getConnection( subStr, i);
				if ( conn != null )  {
					Payload pagePayload = getJSONObjFromConn(conn);
					if ( pagePayload != null ) {
						addTotitles(titles, pagePayload, i, perPage );
					}
				}
			}
			
			Arrays.sort(titles);
			return titles;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String[0];
	}
	
	private static void printArrays( String [] arrays ) {
		for ( int i = 0; i < arrays.length ; i++ ) {
			System.out.println(arrays[i]); 
		}
	}
	
	public static void main(String[] args) {
		
		getMovieTickets("SpiderMan");
		
	}
	

	class Movie {
		
		private String Poster;
		private String Title;
		private String Type;
		private int Year;
		private String imdbID;
		
		public String getPoster() {
			return Poster;
		}
		public void setPoster(String poster) {
			Poster = poster;
		}
		public String getTitle() {
			return Title;
		}
		public void setTitle(String title) {
			Title = title;
		}
		public String getType() {
			return Type;
		}
		public void setType(String type) {
			Type = type;
		}
		public int getYear() {
			return Year;
		}
		public void setYear(int year) {
			Year = year;
		}
		public String getImdbID() {
			return imdbID;
		}
		public void setImdbID(String imdbID) {
			this.imdbID = imdbID;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((Poster == null) ? 0 : Poster.hashCode());
			result = prime * result + ((Title == null) ? 0 : Title.hashCode());
			result = prime * result + ((Type == null) ? 0 : Type.hashCode());
			result = prime * result + Year;
			result = prime * result + ((imdbID == null) ? 0 : imdbID.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Movie other = (Movie) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (Poster == null) {
				if (other.Poster != null)
					return false;
			} else if (!Poster.equals(other.Poster))
				return false;
			if (Title == null) {
				if (other.Title != null)
					return false;
			} else if (!Title.equals(other.Title))
				return false;
			if (Type == null) {
				if (other.Type != null)
					return false;
			} else if (!Type.equals(other.Type))
				return false;
			if (Year != other.Year)
				return false;
			if (imdbID == null) {
				if (other.imdbID != null)
					return false;
			} else if (!imdbID.equals(other.imdbID))
				return false;
			return true;
		}
		private Solution getOuterType() {
			return Solution.this;
		}
		@Override
		public String toString() {
			return "Movie [Poster=" + Poster + ", Title=" + Title + ", Type=" + Type + ", Year=" + Year + ", imdbID="
					+ imdbID + "]";
		}
		
	}
	
	class Payload {
		private int page;
		private int per_page;
		private int total;
		private int total_pages;
		private List<Movie> data;
		public int getPage() {
			return page;
		}
		public void setPage(int page) {
			this.page = page;
		}
		public int getPer_page() {
			return per_page;
		}
		public void setPer_page(int per_page) {
			this.per_page = per_page;
		}
		public int getTotal() {
			return total;
		}
		public void setTotal(int total) {
			this.total = total;
		}
		public int getTotal_pages() {
			return total_pages;
		}
		public void setTotal_pages(int total_pages) {
			this.total_pages = total_pages;
		}
		public List<Movie> getData() {
			return data;
		}
		public void setData(List<Movie> data) {
			this.data = data;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((data == null) ? 0 : data.hashCode());
			result = prime * result + page;
			result = prime * result + per_page;
			result = prime * result + total;
			result = prime * result + total_pages;
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Payload other = (Payload) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (data == null) {
				if (other.data != null)
					return false;
			} else if (!data.equals(other.data))
				return false;
			if (page != other.page)
				return false;
			if (per_page != other.per_page)
				return false;
			if (total != other.total)
				return false;
			if (total_pages != other.total_pages)
				return false;
			return true;
		}
		private Solution getOuterType() {
			return Solution.this;
		}
		@Override
		public String toString() {
			return "Payload [page=" + page + ", per_page=" + per_page + ", total=" + total + ", total_pages="
					+ total_pages + ", data=" + data + "]";
		} 
		
	}

}
