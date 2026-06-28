package restassuredframeworkdesign.models.book_apis.response;

public class GetBookByIdResponse {



        private String book_name;
        private String isbn;
        private String aisle;
        private String author;

        public String getBookName() {
            return book_name;
        }

        public String getIsbn()     {
            return isbn;
        }

        public String getAisle()    {
            return aisle;
        }

        public String getAuthor()   {
            return author;
        }


}
