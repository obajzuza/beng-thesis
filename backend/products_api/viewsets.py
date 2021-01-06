from rest_framework import viewsets, generics, status
from rest_framework.response import Response
from rest_framework.views import APIView
from . import models
from . import serializers

class ProductViewset(viewsets.ModelViewSet):
    queryset = models.Product.objects.all()
    serializer_class = serializers.ProductSerializer

    @classmethod
    def get_extra_actions(self):
        return []

    def get_queryset(self):
        # name = generics.request.query_params.get('name')
        queryset = models.Product.objects.all()
        name = self.request.query_params.get('name', None)
        code = self.request.query_params.get('code', None)
        if name is not None:
            queryset = queryset.filter(product_name__icontains=name)
        if code is not None:
            queryset = queryset.filter(code__contains=code)
        return queryset

class ShelfViewset(viewsets.ModelViewSet):
    queryset = models.Shelf.objects.all()
    serializer_class = serializers.ShelfSerializer

class ProductsOnShelvesViewset(viewsets.ModelViewSet):
    queryset = models.ProductsOnShelves.objects.all()
    serializer_class = serializers.ProductsOnShelvesSerializer

    @classmethod
    def get_extra_actions(self):
        return []

    def get_queryset(self):
        queryset = models.ProductsOnShelves.objects.all()
        shelf = self.request.query_params.get('shelf', None)
        product = self.request.query_params.get('product', None)
        if shelf is not None and product is not None:
            queryset = queryset.filter(shelf__in=shelf, product__in=product)
        return queryset

