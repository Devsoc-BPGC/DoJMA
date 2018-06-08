package io.realm;


public interface com_csatimes_dojma_models_PostRealmProxyInterface {
    public int realmGet$id();
    public void realmSet$id(int value);
    public String realmGet$url();
    public void realmSet$url(String value);
    public String realmGet$title();
    public void realmSet$title(String value);
    public String realmGet$content();
    public void realmSet$content(String value);
    public String realmGet$excerpt();
    public void realmSet$excerpt(String value);
    public RealmList<com.csatimes.dojma.models.Category> realmGet$categories();
    public void realmSet$categories(RealmList<com.csatimes.dojma.models.Category> value);
    public RealmList<com.csatimes.dojma.models.Tag> realmGet$tags();
    public void realmSet$tags(RealmList<com.csatimes.dojma.models.Tag> value);
    public com.csatimes.dojma.models.Author realmGet$author();
    public void realmSet$author(com.csatimes.dojma.models.Author value);
    public RealmList<com.csatimes.dojma.models.Attachment> realmGet$attachments();
    public void realmSet$attachments(RealmList<com.csatimes.dojma.models.Attachment> value);
    public int realmGet$commentCount();
    public void realmSet$commentCount(int value);
    public String realmGet$commentStatus();
    public void realmSet$commentStatus(String value);
    public String realmGet$thumbnail();
    public void realmSet$thumbnail(String value);
    public com.csatimes.dojma.models.Image realmGet$fullThumbnailImage();
    public void realmSet$fullThumbnailImage(com.csatimes.dojma.models.Image value);
    public String realmGet$date();
    public void realmSet$date(String value);
    public String realmGet$modified();
    public void realmSet$modified(String value);
}
