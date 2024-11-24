package com.capgemini.wsb.fitnesstracker.training.internal;


import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/trainings")
public class TrainingController {

    @Autowired
    private TrainingService trainingService;

    @GetMapping
    public List<TrainingDto> getAllTrainings() {
        return trainingService.getAllTrainings();
    }

    @GetMapping("{userId}")
    public List<TrainingDto> getTrainingByUserId(@PathVariable Long userId) {
        return trainingService.getAllTrainingsByUserId(userId);
    }

    @GetMapping("/finished/{afterTime}")
    public List<TrainingDto> getFinishedTrainings(@PathVariable String afterTime){

        return trainingService.getFinishedTrainingsAfter(afterTime);
    }

    @GetMapping("/activityType")
    public List<TrainingDto> getTrainingsByActivityType(@RequestParam ActivityType activityType){
        return trainingService.getTrainingsByActivityType(activityType);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrainingDto createTraining(@RequestBody TrainingDto trainingDto){
        return trainingService.createTraining(trainingDto);
    }

    @PutMapping("/{trainingId}")
    public TrainingDto updateTraining(@PathVariable Long trainingId, @RequestBody TrainingDto trainingDto) {
        return trainingService.updateTraining(trainingId, trainingDto);
    }
}
