package il.ac.shenkar.includes;

/*
 The Category class is a representation of a category in a program.
 It has two instance variables, an id and a category, which are stored as an Integer and a String respectively.
 The id is used to uniquely identify a category, while the category stores the name or label of the category.
 */

public class Category {

    private Integer id;
    private String category;


    public Category(Integer id, String category) {
        this.setId(id);
        this.setCategory(category);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return category ;
    }
}
