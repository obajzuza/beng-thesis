from rest_framework import status
from rest_framework.response import Response
from rest_framework.views import APIView
from . import models
from . import serializers

# Create your views here.


class ProductsOnShelvesUpdateViewset(APIView):
    # ppk - product primary key
    # spk - shelf pirmary key

    def get_object(self, ppk, spk):
        return models.ProductsOnShelves.objects.get(product_id=ppk, shelf_id=spk)

    def patch(self, request, ppk, spk):
        instance = self.get_object(ppk, spk)
        request.data["amount"] += instance.amount
        serializer_class = serializers.ProductsOnShelvesSerializer(instance, data=request.data, partial=True)
        if serializer_class.is_valid():
            serializer_class.save()
            return Response(serializer_class.data, status=status.HTTP_200_OK)
        return Response(serializer_class.errors, status=status.HTTP_400_BAD_REQUEST)

    def put(self, request, ppk, spk):
        try:
            instance = self.get_object(ppk, spk)
        except models.ProductsOnShelves.DoesNotExist:
            product = models.Product.objects.get(id=ppk)
            instance = models.ProductsOnShelves.create(product, request.data["shelf"], request.data["amount"])
        serializer_class = serializers.ProductsOnShelvesSerializer(instance, data=request.data)
        if serializer_class.is_valid():
            serializer_class.save()
            return Response(serializer_class.data, status=status.HTTP_200_OK)
        return Response(serializer_class.errors, status=status.HTTP_400_BAD_REQUEST)
