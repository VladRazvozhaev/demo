package neostudy.demo.controller;

import jakarta.validation.Valid;
import neostudy.demo.dto.ClientDto;
import neostudy.demo.dto.ClientResponseDto;
import neostudy.demo.service.ClientService;
import neostudy.demo.exception.ClientNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ClientResponseDto create(@RequestBody @Valid ClientDto clientDto) {
        return clientService.createClient(clientDto);
    }

    @GetMapping("/{id}")
    public ClientResponseDto getById(@PathVariable UUID id) {
        return clientService.getById(id);
    }

    @GetMapping
    public List<ClientResponseDto> getAll() {
        return clientService.getAll();
    }

    @PutMapping("/{id}")
    public ClientResponseDto update(@PathVariable UUID id,
                                    @RequestBody @Valid ClientDto dto) {
        return clientService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        clientService.delete(id);
    }

    @GetMapping("/hard")
    public void hardMethod() {
        clientService.hardMethod();
    }

    @ExceptionHandler(ClientNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleClientNotFound(ClientNotFoundException e) {
        Map<String, String> response = new HashMap<>();
        response.put("error", e.getMessage());
        return response;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleNotValid(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage())
                );
        return errors;
    }
}
