package com.capgemini.wsb.fitnesstracker.user.internal;

import jakarta.annotation.Nullable;

import java.time.LocalDate;

record UserUpdateDto(@Nullable String firstName, String lastName, LocalDate birthdate, String email){

}