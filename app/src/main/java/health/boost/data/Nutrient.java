package health.boost.data;
//

public class Nutrient {
    private long id;
    private String title;
    private String image;
    private int calories;
    private String protein;
    private String fat;
    private String carbs;


    public Nutrient(String title,
                    String image,
                    int calories,
                    String protein,
                    String fat,
                    String carbs) {
        this.title = title;
        this.image = image;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.carbs = carbs;
    }

//    public int getId() {
//        return id;
//    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCalories() {
        return String.valueOf(calories);
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getCarbs() {
        return carbs;
    }

    public void setCarbs(String carbs) {
        this.carbs = carbs;
    }

    @Override
    public String toString() {
        return "Nutrient{" +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", calories=" + calories +
                ", protein='" + protein + '\'' +
                ", fat='" + fat + '\'' +
                ", carbs='" + carbs + '\'' +
                '}';
    }
}