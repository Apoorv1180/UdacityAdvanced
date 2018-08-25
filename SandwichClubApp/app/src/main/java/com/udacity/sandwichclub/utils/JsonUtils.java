package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static final String NAME = "name";
    public static final String MAIN_NAME = "mainName";
    public static final String ALSO_KNOWN_AS = "alsoKnownAs";
    public static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    public static final String DESCRIPTION = "description";
    public static final String INGREDIENTS = "ingredients";
    public static final String IMAGE = "image";

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();
        try {
            JSONObject root = new JSONObject(json);
            JSONObject nameObj = root.getJSONObject(NAME);
            String mainName = nameObj.getString(MAIN_NAME);
            List<String> alsoKnownAsList = new ArrayList<>();
            JSONArray alsoKnownAs = nameObj.getJSONArray(ALSO_KNOWN_AS);
            for (int i = 0; i < alsoKnownAs.length(); i++) {
                alsoKnownAsList.add(alsoKnownAs.getString(i));
            }
            String placeOfOrigin = root.getString(PLACE_OF_ORIGIN);
            String description = root.getString(DESCRIPTION);
            String image = root.getString(IMAGE);
            List<String> ingredientsList = new ArrayList<>();
            JSONArray ingredients = root.getJSONArray(INGREDIENTS);
            for (int i = 0; i < ingredients.length(); i++) {
                ingredientsList.add(ingredients.getString(i).toString());
            }
            sandwich.setMainName(mainName);
            sandwich.setAlsoKnownAs(alsoKnownAsList);
            sandwich.setPlaceOfOrigin(placeOfOrigin);
            sandwich.setDescription(description);
            sandwich.setImage(image);
            sandwich.setIngredients(ingredientsList);

            return sandwich;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
