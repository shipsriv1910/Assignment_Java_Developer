@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/add")
    public Customer addCustomer(@RequestBody Customer customer) {
        return customerRepo.save(customer);
    }

    @GetMapping("/due")
    public List<Customer> getCustomersWithDueBills() {
        ResponseEntity<Billing[]> response = restTemplate.getForEntity("http://localhost:8081/billing/due", Billing[].class);
        Set<Integer> dueBillIds = Arrays.stream(response.getBody())
                .map(Billing::getBillId)
                .collect(Collectors.toSet());
        return customerRepo.findAll().stream()
                .filter(c -> dueBillIds.contains(c.getCustomerBillId()))
                .collect(Collectors.toList());
    }
}