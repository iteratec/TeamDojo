#!/bin/bash
# Files are ordered in proper order with needed wait for the dependent custom resource definitions to get initialized.
# Usage: bash helm-apply.sh
cs=csvc
suffix=helm
kubectl apply -f namespace.yml
helmVersion=$(helm version --client | grep -E "v3\\.[0-9]{1,3}\\.[0-9]{1,3}" | wc -l)
if [ -d "${cs}-${suffix}" ]; then
  if [ $helmVersion -eq 1 ]; then
helm uninstall ${cs} 2>/dev/null
  else
helm delete --purge ${cs} 2>/dev/null
  fi
helm dep up ./${cs}-${suffix}
  if [ $helmVersion -eq 1 ]; then
helm install ${cs} ./${cs}-${suffix} --replace --namespace teamdojo
  else
helm install --name ${cs} ./${cs}-${suffix} --replace --namespace teamdojo
  fi
fi
  if [ $helmVersion -eq 1 ]; then
helm uninstall teamdojo 2>/dev/null
  else
helm delete --purge teamdojo 2>/dev/null
  fi
helm dep up ./teamdojo-${suffix}
  if [ $helmVersion -eq 1 ]; then
helm install teamdojo  ./teamdojo-${suffix} --replace --namespace teamdojo
  else
helm install --name teamdojo  ./teamdojo-${suffix} --replace --namespace teamdojo
  fi


