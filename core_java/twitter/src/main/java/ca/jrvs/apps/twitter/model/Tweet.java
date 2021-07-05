package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "createdAt",
    "id",
    "idStr",
    "text",
    "entities",
    "coordinates",
    "reTweetCount",
    "favoriteCount",
    "favorited",
    "reTweeted"
})
public class Tweet {

  @JsonProperty("createdAt")
  private String createdAt;
  @JsonProperty("id")
  private long id;
  @JsonProperty("idStr")
  private String idStr;
  @JsonProperty("text")
  private String text;
  @JsonProperty("entities")
  private Entities entities;
  @JsonProperty("coordinates")
  private Coordinates coordinates;
  @JsonProperty("reTweetCount")
  private int reTweetCount;
  @JsonProperty("favoriteCount")
  private Integer favoriteCount;
  @JsonProperty("favorited")
  private boolean favorited;
  @JsonProperty("reTweeted")
  private boolean reTweeted;


  @JsonProperty("createdAt")
  public String getCreatedAt() {
    return createdAt;
  }

  @JsonProperty("createdAt")
  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  @JsonProperty("id")
  public long getId() {
    return id;
  }

  @JsonProperty("id")
  public void setId(long id) {
    this.id = id;
  }

  @JsonProperty("idStr")
  public String getIdStr() {
    return idStr;
  }

  @JsonProperty("idStr")
  public void setIdStr(String idStr) {
    this.idStr = idStr;
  }

  @JsonProperty("text")
  public String getText() {
    return text;
  }

  @JsonProperty("text")
  public void setText(String text) {
    this.text = text;
  }

  @JsonProperty("entities")
  public Entities getEntities() {
    return entities;
  }

  @JsonProperty("entities")
  public void setEntities(Entities entities) {
    this.entities = entities;
  }

  @JsonProperty("coordinates")
  public Coordinates getCoordinates() {
    return coordinates;
  }

  @JsonProperty("coordinates")
  public void setCoordinates(Coordinates coordinates) {
    this.coordinates = coordinates;
  }

  @JsonProperty("reTweetCount")
  public int getReTweetCount() {
    return reTweetCount;
  }

  @JsonProperty("reTweetCount")
  public void setReTweetCount(int reTweetCount) {
    this.reTweetCount = reTweetCount;
  }

  @JsonProperty("favoriteCount")
  public Integer getFavoriteCount() {
    return favoriteCount;
  }

  @JsonProperty("favoriteCount")
  public void setFavoriteCount(Integer favoriteCount) {
    this.favoriteCount = favoriteCount;
  }

  @JsonProperty("favorited")
  public boolean isFavorited() {
    return favorited;
  }

  @JsonProperty("favorited")
  public void setFavorited(boolean favorited) {
    this.favorited = favorited;
  }

  @JsonProperty("reTweeted")
  public boolean isReTweeted() {
    return reTweeted;
  }

  @JsonProperty("reTweeted")
  public void setReTweeted(boolean reTweeted) {
    this.reTweeted = reTweeted;
  }
}
