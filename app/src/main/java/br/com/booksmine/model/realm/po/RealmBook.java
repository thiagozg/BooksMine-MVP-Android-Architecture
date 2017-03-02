package br.com.booksmine.model.realm.po;

import br.com.booksmine.model.pojo.Book;
import br.com.booksmine.model.pojo.ImageLinks;
import br.com.booksmine.model.pojo.VolumeInfo;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Developed by.:   @thiagozg on 23/02/2017.
 * E-mail.:         thiagozg1995@gmail.com
 * GitHub.:         https://github.com/thiagozg/
 * Google Play.:    https://play.google.com/store/apps/developer?id=Thiago+Giacomini
 */

public class RealmBook extends RealmObject
    implements RealmModel {

    public static final String ID = "id";

    @PrimaryKey
    private String id;
    private String title;
    private String subtitle;
    private RealmList<RealmString> authors;
    private String publisher;
    private String description;
    private String publishedDate;
    private int pageCount;
    private String smallThumbnail;
    private String thumbnail;

    public static RealmBook convertBookToPO(Book book) {
        RealmBook realmBook = new RealmBook();
        realmBook.id = book.getId();

        VolumeInfo vInfo = book.getVolumeInfo();
        realmBook.title = vInfo.getTitle();
        realmBook.subtitle = vInfo.getSubtitle();

        realmBook.authors = new RealmList<>();
        for (String author : vInfo.getAuthors()) {
            realmBook.authors.add(new RealmString(author));
        }

        realmBook.publisher = vInfo.getPublisher();
        realmBook.description = vInfo.getDescription();
        realmBook.publishedDate = vInfo.getPublishedDate();
        realmBook.pageCount = vInfo.getPageCount();

        ImageLinks imageLinks = vInfo.getImageLinks();
        realmBook.smallThumbnail = imageLinks.getSmallThumbnail();
        realmBook.thumbnail = imageLinks.getThumbnail();

        return realmBook;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public RealmList<RealmString> getAuthors() {
        return authors;
    }

    public void setAuthors(RealmList<RealmString> authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getSmallThumbnail() {
        return smallThumbnail;
    }

    public void setSmallThumbnail(String smallThumbnail) {
        this.smallThumbnail = smallThumbnail;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

}
