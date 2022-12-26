package framework.orders;

import java.util.ArrayList;

public class CreateOrderRequest {
    private ArrayList<String> ingredients;

    public CreateOrderRequest(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public CreateOrderRequest() {
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void addIngredient(String ingredient) {
        ingredients.add(ingredient);
    }
}
