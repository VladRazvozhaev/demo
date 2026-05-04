package neostudy.demo;

import neostudy.demo.dto.ClientDto;
import neostudy.demo.dto.ClientResponseDto;
import neostudy.demo.model.Client;
import neostudy.demo.repository.ClientRepository;
import neostudy.demo.service.ClientService;
import neostudy.demo.exception.ClientNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DemoApplicationTests {

    @Mock
    ClientRepository clientRepository;

    @InjectMocks
    ClientService clientService;

    UUID id = UUID.randomUUID();

    private Client client = Client.builder()
            .id(id)
            .name("Test")
            .email("test@mail.com")
            .build();

    private ClientDto dto = ClientDto.builder()
            .name("Test")
            .email("test@mail.com")
            .build();

    @Test
    void shouldCreateClient() {
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        ClientResponseDto result = clientService.createClient(dto);

        assertNotNull(result);
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    void shouldReturnAllClients() {
        when(clientRepository.findAll()).thenReturn(List.of(client));

        List<ClientResponseDto> result = clientService.getAll();

        assertEquals(1, result.size());
        verify(clientRepository).findAll();
    }

    @Test
    void shouldFindClientById() {
        when(clientRepository.findById(id)).thenReturn(Optional.of(client));

        Client result = clientService.findClient(id);

        assertNotNull(result);
        verify(clientRepository).findById(id);
    }

    @Test
    void shouldThrowExWhenClientNotFound() {
        when(clientRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> clientService.findClient(id));
    }

    @Test
    void shouldDeleteClient() {
        when(clientRepository.existsById(id)).thenReturn(true);

        clientService.delete(id);

        verify(clientRepository).deleteById(id);
    }

    @Test
    void shouldThrowExWhenTryDeleteMissingClient() {
        when(clientRepository.existsById(id)).thenReturn(false);

        assertThrows(ClientNotFoundException.class, () -> clientService.delete(id));
    }

    @Test
    void shouldUpdateClient() {
        when(clientRepository.findById(id)).thenReturn(Optional.of(client));
        when(clientRepository.save(any())).thenReturn(client);

        ClientResponseDto result = clientService.update(id, dto);

        assertNotNull(result);
        verify(clientRepository).save(any());
    }
}
