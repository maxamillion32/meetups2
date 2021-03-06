package com.dusanjovanov.meetups3.rest;

import com.dusanjovanov.meetups3.models.Contact;
import com.dusanjovanov.meetups3.models.Group;
import com.dusanjovanov.meetups3.models.MeetingResponse;
import com.dusanjovanov.meetups3.models.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by duca on 29/12/2016.
 */

public interface MeetupsApi {
    @GET("users/getByEmail/{email}")
    Call<User> getUser(@Path("email")String email);

    @GET("users/search/{query}")
    Call<ArrayList<User>> searchUsers(@Path("query") String query);

    @FormUrlEncoded
    @POST("users")
    Call<User> createUser(@Field("display_name") String displayName,
                              @Field("email") String email,
                              @Field("photo_url") String photoUrl,
                              @Field("token") String token);

    @GET("users/{id_user}/groups")
    Call<ArrayList<Group>> getGroups(@Path("id_user") int idUser);

    @GET("users/{id_user}/contacts")
    Call<ArrayList<Contact>> getContacts(@Path("id_user") int idUser);

    @GET("users/{id_user}/requests")
    Call<String> getRequests(@Path("id_user") int idUser);

    @POST("users/{id_user1}/contacts/{id_user2}")
    Call<Void> addToContacts(@Path("id_user1")int receivingUser, @Path("id_user2") int sendingUser);

    @DELETE("users/{id_user1}/contactRequests/{id_user2}")
    Call<Void> deleteContactRequest(@Path("id_user1") int receivingUser, @Path("id_user2") int sendingUser);

    @DELETE("groups/{id_group}/memberRequests/{id_user}")
    Call<Void> deleteMemberRequest(@Path("id_group") int idGroup, @Path("id_user") int idUser);

    @GET("groups/{id_group}/meetings")
    Call<String> getGroupMeetings(@Path("id_group") int idGroup);

    @GET("groups/{id_group}")
    Call<Group> getGroup(@Path("id_group") int idGroup);

    @GET("meetings/{id_meeting}/responses")
    Call<List<MeetingResponse>> getMeetingResponses(@Path("id_meeting") int idMeeting);

    @DELETE("users/{id_user1}/contacts/{id_user2}")
    Call<Void> deleteContact(@Path("id_user1") int idUser1, @Path("id_user2") int idUser2);

    @POST("groups/{id_group}/memberRequests/{id_user}")
    Call<Void> addMember(@Path("id_group") int idGroup,@Path("id_user") int idUser);

}
