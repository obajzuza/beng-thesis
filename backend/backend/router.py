from products_api.viewsets import ProductViewset, ShelfViewset, ProductsOnShelvesViewset
from rest_framework import routers

router = routers.DefaultRouter()
router.register('product', ProductViewset, basename='ProductViewset')
router.register('shelf', ShelfViewset)
router.register('products-on-shelves', ProductsOnShelvesViewset)
# router.register('product-shelf', ProductsOnShelvesViewset)

# urls - GET, POST, UPDATE, DELETE

