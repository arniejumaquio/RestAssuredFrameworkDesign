package rahulshettyacademy;

public enum BookAPIResources {

    ADD("Library/Addbook.php"),
    GET_BY_ID("Library/GetBook.php"),
    DELETE("Library/DeleteBook.php");

    private String url;

    BookAPIResources(String url){
        this.url = url;
    }

     public String getUrl(){
        return this.url;
     }

}
