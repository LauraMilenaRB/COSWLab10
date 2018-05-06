# Android Persistance API Integration
Android Local Storage and integration with a web API.

**Part 1: Local storage using ORMLite**

1) Download the project code, import it into Android Studio and make sure it compiles correctly.

2) Check the data structure of the following json resource and add the proper attributes and annotations to the class Team in order to be able to persist the teams data (make sure that the _Team_ class has an empty constructor that OrmLite requires to instantiate the model).


Teams url:   https://raw.githubusercontent.com/sancarbar/starting-android-lists/master/teams.json
 
ORMLite documentation examples: http://ormlite.com/android/examples/

   
   3) Instantiate the _OrmModel_ class in the _MainActivity_ and create some team objects in the database by using the methods provided by the dao. 
   
   4) Close your app and re-run it and verify that the created objects were save on your data base by using the method _getAll()_ from the _TeamDao_ class.
   
   
   
   **Part 2: retrieving data from the API using Retrofit**
   
   1) Add the following dependency on your build.gradle(app) file:
   ```groovy
   compile 'com.squareup.retrofit2:retrofit:2.2.0'
   compile 'com.squareup.retrofit2:converter-gson:2.1.0'
   ```
   
   2) Create an interface called _TeamsService_ that has a get method to retrieve the teams
   
  ```java
      interface TeamsService
      {
      
          @GET( "teams.json" )
          Call<List<Team>> getTeamsList( );
      
      }
  ```
      
3) Create a class called RetrofitNetwork inside a package called network that contains the following code:
 
    
  ```java
     public class RetrofitNetwork
      {
      
       private static final String BASE_URL = "https://raw.githubusercontent.com/sancarbar/starting-android-lists/master/";
       
       private TeamsService teamsService;
      
           public RetrofitNetwork()
              {
                  Retrofit retrofit =
                      new Retrofit.Builder().baseUrl( BASE_URL ).addConverterFactory( GsonConverterFactory.create() ).build();
                  teamsService = retrofit.create( TeamsService.class );
              }
      
      }
  ```
      
4) Create a _Callback_ interface inside the network package to handle the response from the network request:
       
  ```java
     public interface RequestCallback<T>
     {
     
         void onSuccess( T response );
     
         void onFailed( NetworkException e );
     
     }
  ```
                              
5) Add a method called _getTeams_ to the _RetrofitNetwork_ class  to retrieve the Teams list.
               
  ```java
       public void getTeams( RequestCallback<List<Team>> requestCallback )
         {             
             try
             {
                Call<List<Team>> call = teamsService.getTeamsList( );
                 Response<List<Team>> execute = call.execute();
                 requestCallback.onSuccess( execute.body() );
             }
             catch ( IOException e )
             {
                 requestCallback.onFailed( new NetworkException( 0, null, e ) );
             }
         }

  ```
  
  6) Verify that the method works as expected and that the teams are retrieved correctly (you can try using the debugger)
  
  7) Open Android Monitor and look at the exceptions that are shown. As you notice the request does not work for two reasons: You don't have an Internet permission and you are running the network request on the main thread.
  
  8) Add a Internet permission to the AndroidManifest file:
   ```xml
   <manifest xmlns:android="http://schemas.android.com/apk/res/android"
             package="com.gdg.myfirstapp">
   
     <uses-permission android:name="android.permission.INTERNET" />
   
     <application
         android:allowBackup="true"
         android:name=".MyApplication"
         android:icon="@drawable/ic_launcher"
         android:label="@string/app_name"
         android:theme="@style/AppTheme">

   ```
          
  
  9) Create an instance of the ExecutorService class, then call the method submit and inside the run method of the Runnable class add the code that does the network request:
  
   ```java
  ExecutorService executorService = Executors.newFixedThreadPool( 1 );
  
  executorService.execute( new Runnable()
  {
      @Override
      public void run()
      {
          try
          {
              //Network request code goes here
          }
          catch ( IOException e )
          {
              e.printStackTrace();
          }
      }
  } );
```
  
  7) Persist the teams objects into the database, then restart your application and make sure that teams are stored correctly.
  
  
  **Part 3: Display the teams list using a RecyclerView.Adapter**
  
  Reference: https://developer.android.com/training/material/lists-cards.html
  
  1) Create a new package called ui.adapter and then create a class that extends the _RecyclerView.Adapter_ called _TeamsAdapter_. Inside this class create a _ViewHolder_ inner class with the corresponding element views that will represent a team (name, short name and image).
  
  
  2) Include the cardLayout and RecyclerView dependencies on your gradle file.
   ```groovy
    compile 'com.android.support:cardview-v7:21.0.+'
    compile 'com.android.support:recyclerview-v7:21.0.+'
 ```
  
  3) Create an xml file that will represent the row of a team and make sure that these values are mapped on the ViewHolder class created on the step 1.
  

       <?xml version="1.0" encoding="utf-8"?>
       <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
                     xmlns:card_view="http://schemas.android.com/apk/res-auto"
                     xmlns:tools="http://schemas.android.com/tools"
                     android:orientation="horizontal"
                     android:layout_width="match_parent"
                     android:layout_height="wrap_content">
       
         <android.support.v7.widget.CardView
             android:id="@+id/card_view"
             android:layout_gravity="center"
             android:layout_width="match_parent"
             android:layout_height="wrap_content"
             android:layout_marginBottom="4dp"
             android:layout_marginLeft="8dp"
             android:layout_marginStart="8dp"
             android:layout_marginRight="8dp"
             android:layout_marginEnd="8dp"
             android:layout_marginTop="4dp"
             card_view:cardCornerRadius="4dp">
       
           <LinearLayout
               android:orientation="horizontal"
               android:layout_width="match_parent"
               android:layout_height="wrap_content"
               android:padding="10dp">
       
             <ImageView
                 android:id="@+id/logo"
                 android:layout_width="50dp"
                 android:layout_height="50dp"
                 tools:src="@drawable/ic_launcher" />
       
             <LinearLayout
                 android:layout_width="match_parent"
                 android:layout_height="wrap_content"
                 android:orientation="vertical"
                 android:layout_marginLeft="10dp"
                 android:layout_marginStart="10dp">
       
               <TextView
                   android:id="@+id/name"
                   android:layout_width="wrap_content"
                   android:layout_height="wrap_content"
                   tools:text="Borrusia Dortmund"
                   android:textColor="@android:color/black"
                   android:textSize="16sp"
                   android:textStyle="bold" />
       
               <TextView
                   android:id="@+id/shortName"
                   android:layout_width="wrap_content"
                   android:layout_height="wrap_content"
                   tools:text="Borrusia Dortmund"
                   android:textColor="@android:color/black"
                   android:textSize="14sp" />
       
             </LinearLayout>
           </LinearLayout>
         </android.support.v7.widget.CardView>
       </LinearLayout>



4) Modify the main_activity.xml file with the following code to have the recyclerview element:

 ```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
              xmlns:tools="http://schemas.android.com/tools"
              android:layout_width="match_parent"
              android:layout_height="match_parent"
              android:paddingLeft="@dimen/activity_horizontal_margin"
              android:paddingRight="@dimen/activity_horizontal_margin"
              android:paddingTop="@dimen/activity_vertical_margin"
              android:paddingBottom="@dimen/activity_vertical_margin"
              tools:context=".MainActivity"
              android:orientation="vertical">
              

      <android.support.v7.widget.RecyclerView
          android:id="@+id/recyclerView"
          android:layout_width="match_parent"
          android:layout_height="match_parent"
          android:scrollbars="vertical" />
          

</LinearLayout>
```

5) Create a variable for the RecyclerView in the MainActivity class and bind it to the RecyclerView using the method _findViewById(R.id.recyclerView)_

6) Add the following method to your activity and call it right after the _setContentView_ method as follows:
 
 ```java
 
  @Override
     protected void onCreate( Bundle savedInstanceState )
     {
         super.onCreate( savedInstanceState );
         setContentView( R.layout.activity_main );
         configureRecyclerView();
     }


     private void configureRecyclerView()
         {
             recyclerView = (RecyclerView) findViewById( R.id.recyclerView );
             recyclerView.setHasFixedSize( true );
             RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( this );
             recyclerView.setLayoutManager( layoutManager );
         }
```


7) Finally create a TeamsAdapter instance and call the following method of the recyclerView:

 ```java
 
recyclerView.setAdapter( new TeamsAdapter( teams ) );

```

8) Run your application and if you got everything correct you should be able to see the teams list.

