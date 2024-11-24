package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;

import java.util.Date;
import java.util.List;

public interface TrainingService {

    List<TrainingDto> getAllTrainings();
    List<TrainingDto> getAllTrainingsByUserId(Long userId);
    List<TrainingDto> getFinishedTrainingsAfter(String afterTime);
    List<TrainingDto> getTrainingsByActivityType(ActivityType activityType);

    TrainingDto createTraining(TrainingDto trainingDto);
    TrainingDto updateTraining(Long trainingId, TrainingDto trainingDto);

}
