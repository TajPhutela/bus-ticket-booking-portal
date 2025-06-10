package com.team4.backend;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.team4.backend.controller.BookingController;
import com.team4.backend.dto.request.BookingRequestDto;
import com.team4.backend.dto.response.BookingResponseDto;
import com.team4.backend.entities.Booking;
import com.team4.backend.mapper.BookingMapper;
import com.team4.backend.repository.BookingRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private BookingMapper bookingMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllBookings_returnsList() throws Exception {
        Booking b = new Booking(); b.setId(1);
        BookingResponseDto dto = new BookingResponseDto(1, null, 12, "CONFIRMED");
        Mockito.when(bookingRepository.findAll()).thenReturn(Arrays.asList(b));
        Mockito.when(bookingMapper.toResponseDto(b)).thenReturn(dto);

        mockMvc.perform(get("/api/bookings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(1));
    }

    @Test
    void getBookingById_whenExists() throws Exception {
        Booking b = new Booking(); b.setId(2);
        BookingResponseDto dto = new BookingResponseDto(2, null, 5, "PENDING");
        Mockito.when(bookingRepository.findById(2)).thenReturn(Optional.of(b));
        Mockito.when(bookingMapper.toResponseDto(b)).thenReturn(dto);

        mockMvc.perform(get("/api/bookings/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(2));
    }

    @Test
    void getBookingById_notFound() throws Exception {
        Mockito.when(bookingRepository.findById(3)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/bookings/3"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404));
    }

    @Test
    void getBookingsByTripId_whenFound() throws Exception {
        Booking b = new Booking(); b.setId(4);
        BookingResponseDto dto = new BookingResponseDto(4, null, 8, "CONFIRMED");
        Mockito.when(bookingRepository.findByTrip_Id(100)).thenReturn(Arrays.asList(b));
        Mockito.when(bookingMapper.toResponseDto(b)).thenReturn(dto);

        mockMvc.perform(get("/api/bookings/trip_id/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(4));
    }

    @Test
    void getBookingsByTripId_notFound() throws Exception {
        Mockito.when(bookingRepository.findByTrip_Id(200)).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/bookings/trip_id/200"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404));
    }

    @Test
    void getBookingsByStatus_whenFound() throws Exception {
        Booking b = new Booking(); b.setId(5);
        BookingResponseDto dto = new BookingResponseDto(5, null, 1, "CANCELLED");
        Mockito.when(bookingRepository.findByStatus("CANCELLED")).thenReturn(Arrays.asList(b));
        Mockito.when(bookingMapper.toResponseDto(b)).thenReturn(dto);

        mockMvc.perform(get("/api/bookings/status/CANCELLED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].status").value("CANCELLED"));
    }

    @Test
    void getBookingsByStatus_notFound() throws Exception {
        Mockito.when(bookingRepository.findByStatus("UNKNOWN")).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/bookings/status/UNKNOWN"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404));
    }

    @Test
    void getBookingsBySeatNumber_whenFound() throws Exception {
        Booking b = new Booking(); b.setId(6);
        BookingResponseDto dto = new BookingResponseDto(6, null, 42, "CONFIRMED");
        Mockito.when(bookingRepository.findBySeatNumber(42)).thenReturn(Arrays.asList(b));
        Mockito.when(bookingMapper.toResponseDto(b)).thenReturn(dto);

        mockMvc.perform(get("/api/bookings/seat/42"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].seatNumber").value(42));
    }

    @Test
    void getBookingsBySeatNumber_notFound() throws Exception {
        Mockito.when(bookingRepository.findBySeatNumber(99)).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/bookings/seat/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404));
    }

    @Test
    void addBooking_createsNew() throws Exception {
        BookingRequestDto req = new BookingRequestDto(null, 10, 3, "NEW");
        Booking saved = new Booking(); saved.setId(7);
        BookingResponseDto resp = new BookingResponseDto(7, null, 3, "NEW");

        Mockito.when(bookingMapper.toEntity(any())).thenReturn(saved);
        Mockito.when(bookingRepository.save(saved)).thenReturn(saved);
        Mockito.when(bookingMapper.toResponseDto(saved)).thenReturn(resp);

        mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(7))
                .andExpect(jsonPath("$.message").value("Booking Created"));
    }

    @Test
    void updateBooking_whenExists() throws Exception {
        BookingRequestDto req = new BookingRequestDto(null, 11, 4, "UPDATED");
        Booking existing = new Booking(); existing.setId(8);
        Booking updated = new Booking(); updated.setId(8);
        BookingResponseDto resp = new BookingResponseDto(8, null, 4, "UPDATED");

        Mockito.when(bookingRepository.existsById(8)).thenReturn(true);
        Mockito.when(bookingMapper.toEntity(any())).thenReturn(existing);
        Mockito.when(bookingRepository.save(existing)).thenReturn(updated);
        Mockito.when(bookingMapper.toResponseDto(updated)).thenReturn(resp);

        mockMvc.perform(put("/api/bookings/8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(8))
                .andExpect(jsonPath("$.message").value("Booking updated successfully"));
    }

    @Test
    void updateBooking_notFound() throws Exception {
        BookingRequestDto req = new BookingRequestDto(null, 12, 5, "X");
        Mockito.when(bookingRepository.existsById(9)).thenReturn(false);

        mockMvc.perform(put("/api/bookings/9")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404));
    }
}
