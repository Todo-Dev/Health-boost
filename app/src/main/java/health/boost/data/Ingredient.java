package health.boost.data;

public class Ingredient {
    private long id;
    private String name;
    private String image;
    private int nutrientsTitle;



    public Ingredient(String name,
                    String image,
                    int nutrientsTitle) {
        this.name = name;
        this.image = image;
        this.nutrientsTitle = nutrientsTitle;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getNutrientsTitle() {
        return nutrientsTitle;
    }

    public void setNutrientsTitle(int nutrientsTitle) {
        this.nutrientsTitle = nutrientsTitle;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", nutrientsTitle=" + nutrientsTitle +
                '}';
    }
}
