package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.*;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.internal.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


// TODO: Provide Impl
@Service
public class TrainingServiceImpl implements TrainingProvider, TrainingService {

    @Autowired
    private TrainingRepository trainingRepository;
    @Autowired
    private UserRepository userRepository;


    public List<TrainingDto> getAllTrainings(){
        return trainingRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> getTraining(final Long trainingId) {
        throw new UnsupportedOperationException("Not finished yet");
    }

    @Override
    public List<TrainingDto> getAllTrainingsByUserId(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return trainingRepository.findByUserId(user.getId())
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TrainingDto> getFinishedTrainingsAfter(String afterTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(afterTime, formatter);

        LocalDateTime localDateTime = localDate.atStartOfDay();

        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());



        return trainingRepository.findByEndTimeAfter(date)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TrainingDto> getTrainingsByActivityType(ActivityType activityType){
        return trainingRepository.findByActivityType(activityType)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public TrainingDto createTraining(TrainingDto trainingDto){

        Training training = new Training(
                trainingDto.getUser(),
                trainingDto.getStartTime(),
                trainingDto.getEndTime(),
                trainingDto.getActivityType(),
                trainingDto.getDistance(),
                trainingDto.getAverageSpeed()
        );


        training = trainingRepository.save(training);

        return convertToDto(training);
    }


    @Override
    public TrainingDto updateTraining(Long trainingId, TrainingDto trainingDto){
        Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new TrainingNotFoundException(trainingId));

        training.setActivityType(trainingDto.getActivityType());
        training.setDistance(trainingDto.getDistance());
        training.setAverageSpeed(trainingDto.getAverageSpeed());
        training.setStartTime(trainingDto.getStartTime());
        training.setEndTime(trainingDto.getEndTime());

        training = trainingRepository.save(training);
        return convertToDto(training);
    }

    private TrainingDto convertToDto(Training training) {
        TrainingDto dto = new TrainingDto();
        dto.setId(training.getId());
        dto.setUser(training.getUser());
        dto.setStartTime(training.getStartTime());
        dto.setEndTime(training.getEndTime());
        dto.setActivityType(training.getActivityType());
        dto.setDistance(training.getDistance());
        dto.setAverageSpeed(training.getAverageSpeed());
        return dto;
    }

}
