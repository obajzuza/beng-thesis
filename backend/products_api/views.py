from django.shortcuts import render
from rest_framework import viewsets, generics, status
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
        serializer_class = serializers.ProductsOnShelvesSerializer(instance, data=request.data, partial=True)
        if serializer_class.is_valid():
            serializer_class.save()
            return Response(serializer_class.data, status=status.HTTP_200_OK)
        return Response(serializer_class.errors, status=status.HTTP_400_BAD_REQUEST)
