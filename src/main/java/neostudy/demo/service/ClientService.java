package neostudy.demo.service;

import neostudy.demo.dto.ClientDto;
import neostudy.demo.dto.ClientResponseDto;
import neostudy.demo.model.Client;
import neostudy.demo.repository.ClientRepository;
import neostudy.demo.exception.ClientNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClientService {
    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);
    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientResponseDto createClient(ClientDto clientDto) {
        try {
            logger.trace("Entering createClient. ClientDto: {}", clientDto);

            Client client = createClientFromDto(clientDto);
            Client clientInDb = clientRepository.save(client);

            logger.debug("Client {} created and saved", client.getName());
            return createClientResponseDto(clientInDb);

        } catch (Exception e) {
            logger.error("Error creating client: {}", clientDto, e);
            throw e;
        }
    }

    public ClientResponseDto update(UUID uuid, ClientDto clientDto) {
        logger.trace("Entering update client id = {}", uuid);

        Client clientFound = findClient(uuid);

        clientFound.setName(clientDto.getName());
        clientFound.setEmail(clientDto.getEmail());

        logger.info("Client with id = {} updated", uuid);

        return createClientResponseDto(clientRepository.save(clientFound));
    }

    public List<ClientResponseDto> getAll() {
        logger.trace("Entering getAll clients");
        logger.debug("Found all clients from DB");
        List<Client> clients = clientRepository.findAll();
        return clients.stream()
                .map(this::createClientResponseDto)
                .toList();
    }


    public void delete(UUID uuid) {
        logger.trace("Entering delete client with id={}", uuid);

        if (!clientRepository.existsById(uuid)) {
            logger.warn("Client to delete with id = {} not found", uuid);
            throw new ClientNotFoundException("Client not found");
        }

        clientRepository.deleteById(uuid);

        logger.info("Client with id = {}  deleted", uuid);
    }

    public ClientResponseDto getById(UUID uuid) {
        logger.trace("Entering getById client with id={}", uuid);
        Client client = findClient(uuid);
        return createClientResponseDto(client);
    }

    public void hardMethod() {
        long start = System.currentTimeMillis();
        logger.info("Starting hardMethod");

        for (int i = 0; i < 10000000; i++) {
            System.out.println("Итерация " + i + ": " + Math.sqrt(i));
        }

        logger.info("Finished hardMethod in {} ms", System.currentTimeMillis() - start);
    }

    public Client findClient(UUID uuid) {
        logger.trace("Entering getClient with id = {}", uuid);

        Client clientInDb = clientRepository.findById(uuid).orElse(null);
        if (clientInDb == null) {
            logger.warn("Client with id {} not found", uuid);
            throw new ClientNotFoundException("Client not found");
        }

        logger.debug("Client with id:{} found", uuid);
        return clientInDb;
    }

    private Client createClientFromDto(ClientDto clientDto) {
        logger.trace("Entering create");
        return Client.builder()
                .name(clientDto.getName())
                .email(clientDto.getEmail())
                .build();
    }

    private ClientResponseDto createClientResponseDto(Client client) {
        return ClientResponseDto.builder()
                .id(client.getId())
                .name(client.getName())
                .email(client.getEmail())
                .build();
    }
}
