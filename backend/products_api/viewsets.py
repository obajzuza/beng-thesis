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
        if name is not None:
            queryset = queryset.filter(product_name__icontains=name)
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

# class ProductsOnShelvesUpdateViewset(APIView):
#     # ppk - product primary key
#     # spk - shelf pirmary key
#     def get_object(self, ppk, spk):
#         return models.ProductsOnShelves.objects.get(ppk=ppk, spk=spk)
#
#     def patch(self, request, ppk, spk):
#         instance = self.get_object(ppk, spk)
#         serializer_class = serializers.ProductsOnShelvesSerializer(instance, data=request.data, partial=True)
#         if serializer_class.is_valid():
#             serializer_class.save()
#             return Response(serializer_class.data, status=status.HTTP_200_OK)
#         return Response(serializer_class.errors, status=status.HTTP_400_BAD_REQUEST)

# class ProductViewset(generics.ListAPIView):
#     # queryset = models.Product.objects.all()
#     serializer_class = serializers.ProductSerializer
#
#     def get_extra_actions(self):
#         return []
#
#     def get_queryset(self):
#         # name = generics.request.query_params.get('name')
#         queryset = models.Product.objects.all()
#         name = self.request.query_params.get('name', None)
#         if name is not None:
#             queryset = queryset.filter(string__contains=name)
#         return queryset
