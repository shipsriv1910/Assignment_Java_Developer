@RestController
@RequestMapping("/billing")
public class BillingController {
    @Autowired
    private BillingRepository billingRepo;

    @PostMapping("/add")
    public Billing addBilling(@RequestBody Billing billing) {
        return billingRepo.save(billing);
    }

    @GetMapping("/due")
    public List<Billing> getDueBills() {
        return billingRepo.findAll().stream()
                .filter(b -> b.getStatus().equalsIgnoreCase("Due"))
                .collect(Collectors.toList());
    }
}