package com.example.animal_restful_api.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.animal_restful_api.models.AnimalEn;
import java.util.List;

public class SearchViewModelEn extends ViewModel {
    // MutableLiveData to hold the list of animals
    private final MutableLiveData<List<AnimalEn>> animalList = new MutableLiveData<>();

    // MutableLiveData to hold the last animal information
    private final MutableLiveData<String> lastAnimalInfo = new MutableLiveData<>();

    // Getter method for the list of animals
    public MutableLiveData<List<AnimalEn>> getAnimalList() {
        return animalList;
    }

    // Getter method for the last animal information
    public MutableLiveData<String> getLastAnimalInfo() {
        return lastAnimalInfo;
    }

    // Setter method to update the list of animals
    public void setAnimalList(List<AnimalEn> animals) {
        animalList.setValue(animals);
    }

    // Setter method to update the last animal information
    public void setLastAnimalInfo(String info) {
        lastAnimalInfo.setValue(info);
    }
}
