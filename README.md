# Flickr APP

### A Simple App to demonstrate paginated call using Android Paging Library 


This App follows MVVM architecture along with repository as an abstraction layer which interacts with  Network to fetch the data. 
It uses Android paging library to perform endless scroll.

## TechStack used in this app
1. Retrofit and Okhttp  : For Networking
2. Espresso: For activity testing with all states
3. Mockito : For Unit Testing of FlickrApi
4. Dagger2 : For dependency injection
5. Paging : For pagingination (i.e., PagedListAdapter for recyclerview and PageKeyedDataSource for network calls)

Every response from network  are wrapped in a single data type **DATA**, with **NetworkState** as a field which defines the state in which the call is.


