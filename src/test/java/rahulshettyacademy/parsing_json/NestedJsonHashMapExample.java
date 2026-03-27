package rahulshettyacademy.parsing_json;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NestedJsonHashMapExample {

    @Test
    public void nestedJsonExample() {
        
        // Example 1: Simple nested JSON
        String simpleNestedJson = """
        {
            "user": {
                "id": 101,
                "name": "John Doe",
                "email": "john@example.com",
                "address": {
                    "street": "123 Main St",
                    "city": "New York",
                    "zipcode": "10001"
                }
            }
        }
        """;
        
      JsonPath jsonPath = new JsonPath(simpleNestedJson);
      HashMap<String,Object> userMap = jsonPath.get("user");
      System.out.println( "ID: = "+userMap.get("id") );
      System.out.println( "Name: = "+userMap.get("name") );
      System.out.println( "Email: =" +userMap.get("email") );
      HashMap<String,Object> addressMap = (HashMap<String, Object>) userMap.get("address");
      System.out.println("Street: ="+addressMap.get("street") );
      System.out.println("City: ="+addressMap.get("city"));
      System.out.println("Zipcode: ="+addressMap.get("zipcode"));
        

    }
    
    @Test
    public void complexNestedJsonWithArrays() {
        
        // Example 2: Complex nested JSON with arrays
        String complexJson = """
        {
            "company": {
                "name": "Tech Corp",
                "employees": [
                    {
                        "id": 1,
                        "name": "Alice",
                        "department": {
                            "name": "Engineering",
                            "location": "Building A"
                        },
                        "skills": ["Java", "Python", "AWS"]
                    },
                    {
                        "id": 2,
                        "name": "Bob",
                        "department": {
                            "name": "Marketing",
                            "location": "Building B"
                        },
                        "skills": ["SEO", "Content Writing"]
                    }
                ]
            }
        }
        """;
        
        JsonPath jp2 = new JsonPath(complexJson);
        
        // Get company as HashMap
        HashMap<String, Object> company = jp2.get("company");
        System.out.println("Company Name: " + company.get("name"));
        
        // Get employees array as List of HashMaps
        List<HashMap<String, Object>> employees = jp2.getList("company.employees");
        
        for (HashMap<String, Object> employee : employees) {
            System.out.println("\nEmployee ID: " + employee.get("id"));
            System.out.println("Employee Name: " + employee.get("name"));
            
            // Access nested department
            HashMap<String, String> dept = (HashMap<String, String>) employee.get("department");
            System.out.println("Department: " + dept.get("name"));
            System.out.println("Location: " + dept.get("location"));
            
            // Access skills array
            List<String> skills = (List<String>) employee.get("skills");
            System.out.println("Skills: " + String.join(", ", skills));
        }
        
        System.out.println("\n--- Example 3 ---\n");
    }
    
    @Test
    public void deeplyNestedJsonExample() {
        
        // Example 3: Deeply nested JSON (3+ levels)
        String deepJson = """
        {
            "store": {
                "name": "SuperMart",
                "inventory": {
                    "electronics": {
                        "laptops": [
                            {
                                "brand": "Dell",
                                "model": "XPS 13",
                                "price": 1200,
                                "specs": {
                                    "ram": "16GB",
                                    "storage": "512GB SSD",
                                    "processor": {
                                        "brand": "Intel",
                                        "model": "i7-1165G7",
                                        "cores": 4
                                    }
                                }
                            }
                        ]
                    }
                }
            }
        }
        """;
        
        JsonPath jp3 = new JsonPath(deepJson);
        
        // Method 1: Using HashMap navigation
        HashMap<String, Object> store = jp3.get("store");
        HashMap<String, Object> inventory = (HashMap<String, Object>) store.get("inventory");
        HashMap<String, Object> electronics = (HashMap<String, Object>) inventory.get("electronics");
        List<HashMap<String, Object>> laptops = (List<HashMap<String, Object>>) electronics.get("laptops");
        
        HashMap<String, Object> firstLaptop = laptops.get(0);
        System.out.println("Brand: " + firstLaptop.get("brand"));
        System.out.println("Model: " + firstLaptop.get("model"));
        
        HashMap<String, Object> specs = (HashMap<String, Object>) firstLaptop.get("specs");
        HashMap<String, Object> processor = (HashMap<String, Object>) specs.get("processor");
        System.out.println("Processor: " + processor.get("brand") + " " + processor.get("model"));
        System.out.println("Cores: " + processor.get("cores"));
        
        // Method 2: Using direct path (easier!)
        String processorBrand = jp3.getString("store.inventory.electronics.laptops[0].specs.processor.brand");
        Integer cores = jp3.getInt("store.inventory.electronics.laptops[0].specs.processor.cores");
        System.out.println("\nDirect access - Processor Brand: " + processorBrand);
        System.out.println("Direct access - Cores: " + cores);
        
        System.out.println("\n--- Example 4 ---\n");
    }
    
    @Test
    public void mixedNestedStructures() {
        
        // Example 4: Mixed nested structures (objects + arrays)
        String mixedJson = """
        {
            "order": {
                "orderId": "ORD-12345",
                "customer": {
                    "name": "Jane Smith",
                    "contact": {
                        "email": "jane@example.com",
                        "phones": [
                            {"type": "mobile", "number": "555-1234"},
                            {"type": "home", "number": "555-5678"}
                        ]
                    }
                },
                "items": [
                    {
                        "productId": "P001",
                        "name": "Laptop",
                        "quantity": 1,
                        "pricing": {
                            "unitPrice": 1000,
                            "discount": 100,
                            "tax": 90
                        }
                    },
                    {
                        "productId": "P002",
                        "name": "Mouse",
                        "quantity": 2,
                        "pricing": {
                            "unitPrice": 25,
                            "discount": 5,
                            "tax": 4
                        }
                    }
                ]
            }
        }
        """;
        
        JsonPath jp4 = new JsonPath(mixedJson);
        
        // Access using HashMap
        HashMap<String, Object> order = jp4.get("order");
        System.out.println("Order ID: " + order.get("orderId"));
        
        HashMap<String, Object> customer = (HashMap<String, Object>) order.get("customer");
        System.out.println("Customer: " + customer.get("name"));
        
        HashMap<String, Object> contact = (HashMap<String, Object>) customer.get("contact");
        List<HashMap<String, String>> phones = (List<HashMap<String, String>>) contact.get("phones");
        
        for (HashMap<String, String> phone : phones) {
            System.out.println(phone.get("type") + ": " + phone.get("number"));
        }
        
        // Calculate total from items
        List<HashMap<String, Object>> items = (List<HashMap<String, Object>>) order.get("items");
        double total = 0;
        
        for (HashMap<String, Object> item : items) {
            System.out.println("\nProduct: " + item.get("name"));
            int quantity = (Integer) item.get("quantity");
            
            HashMap<String, Integer> pricing = (HashMap<String, Integer>) item.get("pricing");
            int unitPrice = pricing.get("unitPrice");
            int discount = pricing.get("discount");
            int tax = pricing.get("tax");
            
            double itemTotal = (unitPrice - discount + tax) * quantity;
            System.out.println("Item Total: $" + itemTotal);
            total += itemTotal;
        }
        
        System.out.println("\nOrder Total: $" + total);
    }
    
    @Test
    public void workingWithGenericMaps() {
        
        // Example 5: Using Map interface (more flexible)
        String json = """
        {
            "data": {
                "level1": {
                    "level2": {
                        "level3": {
                            "value": "deeply nested value"
                        }
                    }
                }
            }
        }
        """;
        
        JsonPath jp = new JsonPath(json);
        
        // Using Map interface instead of HashMap
        Map<String, Object> data = jp.get("data");
        Map<String, Object> level1 = (Map<String, Object>) data.get("level1");
        Map<String, Object> level2 = (Map<String, Object>) level1.get("level2");
        Map<String, Object> level3 = (Map<String, Object>) level2.get("level3");
        
        System.out.println("Nested Value: " + level3.get("value"));
        
        // Or simply use direct path
        String value = jp.getString("data.level1.level2.level3.value");
        System.out.println("Direct Access: " + value);
    }
}
