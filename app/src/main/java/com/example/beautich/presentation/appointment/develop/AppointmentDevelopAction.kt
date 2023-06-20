package com.example.beautich.presentation.appointment.develop

sealed class AppointmentDevelopAction {

    object NavigateBack : AppointmentDevelopAction()

    object NavigateToServiceSelection : AppointmentDevelopAction()
}
