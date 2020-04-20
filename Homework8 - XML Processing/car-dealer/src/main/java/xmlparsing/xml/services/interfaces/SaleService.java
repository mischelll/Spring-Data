package xmlparsing.xml.services.interfaces;


import org.springframework.stereotype.Service;
import xmlparsing.xml.domain.dtos.sales.SalesViewRootDto;

import java.util.List;

@Service
public interface SaleService {
void seedSales();

SalesViewRootDto getSalesWithDiscount();
}
